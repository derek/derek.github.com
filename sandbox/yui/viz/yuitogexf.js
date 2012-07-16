#!/usr/bin/env node

var data = require('./yui3');
var edges = [];
var id = 0;
var nodes = [];

for(var module in data){
	data[module].id = id;
	data[module].name = module;
	id++;
}

generateNodes();
generateEdges();

var subs = {
	nodes: nodes.join(''),
	edges: edges.join(''),
	nodeCount: nodes.length,
	edgeCount: edges.length
}

var xml = getTemplate('main', subs);

console.log(xml);




function generateNodes() {
	for(var name in data){
		
		var module = data[name];
		var deps = module.requires;
		var name = module.name;
		var id = data[name].id;
		var size = 1;
		if (data[name].requires) {
			size = (data[name].requires.length + 10)
		}
		var subs = {
			name: name,
			id: id,
			x: Math.floor(Math.random()*400),
			y: Math.floor(Math.random()*200),
			size: size
		};
		var xml = getTemplate('node', subs);

		nodes.push(xml);
	}
}

function generateEdges() {
	for(var name in data){
		
		var module = data[name];
		var source = module.id;
		var deps = module.requires;
		var name = module.name;
		var xml;

		for(var j in deps) {
			if (data[deps[j]]) {
				var subs = {
					source: source,
					target: data[deps[j]].id,
					i: edges.length
				};

				edges.push(getTemplate('edge', subs));
			}
		}
	}
}


function getTemplate (name, subs) {
    var fs = require('fs'),
		handlebars = require('handlebars'),
        file = 'templates/' + name + '.txt',
        source = fs.readFileSync(file, 'ascii'),
        template = handlebars.compile(source);

    return template(subs);
}