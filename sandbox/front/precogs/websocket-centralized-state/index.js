const app = require('express')();
const http = require('http').Server(app);
const io = require('socket.io')(http);

const precogs = {};

app.get('/', (req, res) => {
  res.sendFile(__dirname + '/index.html');
});

io.on('connection', (socket) => {
  io.emit('precog-state', precogs);

  socket.on('update-status', (message) => {
    precogs[message.conversationID] = precogs[message.conversationID] || {};
    precogs[message.conversationID][message.user] = message.enable ? Date.now() : 0;
    io.emit('precog-state', precogs);
  });

});

http.listen(9050, () => {
  console.log('listening on *:9050');
});
