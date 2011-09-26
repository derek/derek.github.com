var Embeditor = {
    getInstance: function(id){
      id = id || "embeditor";
      return document.getElementById(id).contentWindow.editor.getSession();
    }
}