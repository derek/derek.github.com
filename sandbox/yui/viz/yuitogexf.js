var data = require('./yui3');
var id = 0;
var nodes = [];
var edges = [];
var clusters = {};

for(var name in data){
	data[name].id = id;
	data[name].name = name;
	data[name].count = 0;
	data[name].cluster = nameToCluster(name);
	clusters[nameToCluster(name)] = {};
	id++;
}

for(var component in clusters) {
	clusters[component].x = Math.floor(Math.random() * 400);
	clusters[component].y = Math.floor(Math.random() * 200);
	clusters[component].color = getRandomRGB();
}

generateEdges();
generateNodes();

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
		var clusterName = nameToCluster(name);

		var color = clusters[data[name].cluster].color;
		var id = data[name].id;
		var size = data[name].count;

		var subs = {
			name: name,
			id: id,
			x: clusters[clusterName].x + (Math.floor(Math.random()*15)),
			y: clusters[clusterName].y + (Math.floor(Math.random()*15)),
			size: (size > 10) ? 15 : size,
			color: color
		};

		var xml = getTemplate('node', subs);

		nodes.push(xml);
	}
}

function generateEdges() {
	for(var name in data){
		var module = data[name];
		if (module.requires) {
			var dependsOn = data[name].requires;
			for(var j=0; j < dependsOn.length; j++) {
				var d = data[name].requires[j];
				if (data[d]) {
					data[d].count++;
				}
				else {
					// deps shouldn't fall into here.  If they do, fix the metadata
					// console.log(d);
				}
			}
		}
	}

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

function nameToCluster (name) {
	return name.split('-')[0];
}

function getRandomRGB() {
	var r = Math.floor(Math.random()*255);
	var g = Math.floor(Math.random()*255);
	var b = Math.floor(Math.random()*255);
	return 'b="' + b + '" g="' + g + '" r="' + r + '"'
}


	
	// switch (component) {
	// 	// // Infrastucture
	// 	case "app":
	// 	case "attribute":
	// 	case "base":
	// 	case "widget":
	// 	case "event":
	// 	case "plugin":
	// 	case "view":
	// 	case "model":
	// 	case "yui":
	// 		clusters[component].color = getRandomRGB();
	// 	break;	


	// 	// // Utilities
	// 	// 	data[name].color = 'b="15" g="122" r="216"';
	// 	// break;	

	// 	// // Widgets
	// 	case "panel":
	// 	case "slider":
	// 	case "dial":
	// 	case "panel":
	// 	case "calendar":
	// 	case "button":
	// 		clusters[component].color = getRandomRGB();
	// 	break;

		
	// 	case "align":
	// 	case "anim":
	// 	case "array":
	// 	case "arraylist":
	// 	case "arraysort":
	// 	case "async":
	// 	case "autocomplete":
	// 	case "cache":
	// 	case "calendarnavigator":
	// 	case "charts":
	// 	case "classnamemanager":
	// 	case "clickable":
	// 	case "collection":
	// 	case "console":
	// 	case "controller":
	// 	case "cookie":
	// 	case "createlink":
	// 	case "cssbase":
	// 	case "cssbutton":
	// 	case "cssfonts":
	// 	case "cssgrids":
	// 	case "cssreset":
	// 	case "dataschema":
	// 	case "datasource":
	// 	case "datatable":
	// 	case "datatype":
	// 	case "dd":
	// 	case "dom":
	// 	case "dump":
	// 	case "editor":
	// 	case "escape":
	// 	case "exec":
	// 	case "features":
	// 	case "file":
	// 	case "frame":
	// 	case "gesture":
	// 	case "get":
	// 	case "graphics":
	// 	case "handlebars":
	// 	case "highlight":
	// 	case "history":
	// 	case "imageloader":
	// 	case "intl":
	// 	case "io":
	// 	case "json":
	// 	case "jsonp":
	// 	case "lazy":
	// 	case "loader":
	// 	case "matrix":
	// 	case "node":
	// 	case "oop":
	// 	case "overlay":
	// 	case "parallel":
	// 	case "pjax":
	// 	case "pluginhost":
	// 	case "profiler":
	// 	case "querystring":
	// 	case "queue":
	// 	case "range":
	// 	case "recordset":
	// 	case "resize":
	// 	case "router":
	// 	case "scrollview":
	// 	case "selector":
	// 	case "shim":
	// 	case "sortable":
	// 	case "stylesheet":
	// 	case "substitute":
	// 	case "swf":
	// 	case "swfdetect":
	// 	case "tabview":
	// 	case "test":
	// 	case "text":
	// 	case "transition":
	// 	case "uploader":
	// 	case "yql":

	// 	default: 
	// 		clusters[component].color = getRandomRGB();
	// 		break;
	// }

	// console.log('case "' + component + '":');