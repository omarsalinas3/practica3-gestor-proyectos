const test = require('node:test');
const assert = require('node:assert');
const { pagina, espera } = require('./dom');

const proyectos = [
  { id_proyecto: 1, nombre: 'Proyecto A', descripcion: 'Primero', fecha_inicio: '2026-01-01', fecha_finalizacion: '2026-02-01', estado_proyecto: 'En proceso', fondo: '/Images/blanco.png' },
  { id_proyecto: 2, nombre: 'Proyecto B', descripcion: 'Segundo', fecha_inicio: '2026-03-01', fecha_finalizacion: '2026-04-01', estado_proyecto: 'Finalizado', fondo: '/Images/blanco.png' },
];

test('AUT-E03 Componente / Vista: la consulta finaliza correctamente y los datos se presentan en la interfaz', async () => {
  const w = pagina('<div id="cardContainer"></div>', ['inicio.js'], (o) => o.success(proyectos));
  await espera(50);

  assert.strictEqual(w.__ajax[0].url, '/consultarProyectos');
  const titulos = [...w.document.querySelectorAll('#cardContainer .card-title')].map((e) => e.textContent);
  assert.deepStrictEqual(titulos, ['Proyecto A', 'Proyecto B']);
  assert.match(w.document.getElementById('cardContainer').textContent, /Estado del proyecto: Finalizado/);
});

test('AUT-F03 Componente / Vista: el recurso solicitado no existe, la interfaz mantiene un estado controlado e informa el resultado', async () => {
  const w = pagina('<div id="cardContainer"></div>', ['inicio.js'], (o) => o.error({ status: 404 }));
  w.console.error = () => {};
  await espera(50);

  const contenedor = w.document.getElementById('cardContainer');
  assert.strictEqual(contenedor.querySelectorAll('.card').length, 0, 'no debe mostrar tarjetas');
  assert.match(contenedor.textContent, /No se pudieron cargar los proyectos/, 'debe informar el resultado al usuario');
});
