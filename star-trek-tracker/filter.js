// Progressive enhancement: filter the grid by status without a reload.
// The page is complete without this file. Nothing here is needed for first paint.
(function () {
  var grid = document.getElementById('grid');
  var box = document.getElementById('filters');
  var out = document.getElementById('count');
  if (!grid || !box || !out) return;

  var cards = Array.prototype.slice.call(grid.children);
  var options = [['all', 'All']].concat(
    JSON.parse(box.getAttribute('data-options')).map(function (o) { return [o[0], o[1]]; })
  );

  box.setAttribute('role', 'group');
  box.setAttribute('aria-labelledby', 'filter-label');
  var html = '<span class="lab" id="filter-label">Show technologies with a capability that is</span>';
  options.forEach(function (o) {
    html += '<button type="button" data-f="' + o[0] + '" aria-pressed="false">' + o[1] + '</button>';
  });
  box.innerHTML = html;
  var buttons = Array.prototype.slice.call(box.querySelectorAll('button'));

  function apply(f) {
    var shown = 0;
    cards.forEach(function (card) {
      var rows = card.querySelectorAll('li[data-status]');
      var match = f === 'all';
      Array.prototype.forEach.call(rows, function (row) {
        var hit = f !== 'all' && row.getAttribute('data-status') === f;
        row.classList.toggle('hit', hit);
        if (hit) match = true;
      });
      card.hidden = !match;
      if (match) shown++;
    });
    buttons.forEach(function (b) { b.setAttribute('aria-pressed', String(b.getAttribute('data-f') === f)); });
    out.textContent = 'Showing ' + shown + ' of ' + cards.length + ' technologies';
  }

  function fromHash() {
    var h = location.hash.slice(1);
    return options.some(function (o) { return o[0] === h; }) ? h : 'all';
  }

  box.addEventListener('click', function (e) {
    var b = e.target.closest('button');
    if (!b) return;
    var f = b.getAttribute('data-f');
    apply(f);
    try { history.replaceState(null, '', f === 'all' ? location.pathname + location.search : '#' + f); } catch (err) {}
  });
  window.addEventListener('hashchange', function () { apply(fromHash()); });
  apply(fromHash());
})();
