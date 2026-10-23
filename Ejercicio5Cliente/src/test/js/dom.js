// Utilidades: carga un fragmento HTML y los scripts reales del frontend en jsdom.
const { JSDOM } = require('jsdom');
const fs = require('fs');
const path = require('path');

const JS = path.join(__dirname, '../../main/resources/static/js');

function pagina(html, scripts, alAjax) {
  const dom = new JSDOM(`<!DOCTYPE html><html><body>${html}</body></html>`, {
    runScripts: 'outside-only', url: 'http://localhost:8080/', pretendToBeVisual: true,
  });
  const w = dom.window;
  w.eval(fs.readFileSync(path.join(JS, 'jquery-3.7.1.min.js'), 'utf8'));
  w.__toasts = [];
  w.__ajax = [];
  w.bootstrap = { Toast: class { constructor(el) { this.el = el; } show() { w.__toasts.push(this.el.id); } } };
  // Se sustituye $.ajax para simular las respuestas del backend
  w.$.ajax = (opciones) => { w.__ajax.push(opciones); if (alAjax) alAjax(opciones); };
  scripts.forEach((s) => w.eval(fs.readFileSync(path.join(JS, s), 'utf8')));
  return w;
}

const espera = (ms) => new Promise((r) => setTimeout(r, ms));
module.exports = { pagina, espera };
