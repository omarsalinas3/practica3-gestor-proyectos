const test = require('node:test');
const assert = require('node:assert');
const { pagina } = require('./dom');

const LOGIN = `
  <input type="email" id="exampleInputEmail1"><input type="password" id="exampleInputPassword1">
  <div id="alerta1"></div><div id="alerta2"></div><div id="alerta3"></div><div id="alerta4"></div><div id="alerta5"></div>`;

test('AUT-E02 Formulario: se proporcionan datos válidos, la validación permite enviar la información', () => {
  const w = pagina(LOGIN, ['login.js']);
  w.document.getElementById('exampleInputEmail1').value = 'omar@gmail.com';
  w.document.getElementById('exampleInputPassword1').value = '1234';

  w.validarLogin();

  assert.deepStrictEqual(w.__toasts, [], 'no debe mostrarse ninguna alerta');
  assert.strictEqual(w.__ajax.length, 1, 'debe enviarse la petición al backend');
  assert.strictEqual(w.__ajax[0].url, '/verificarLogin');
  assert.deepStrictEqual(JSON.parse(w.__ajax[0].data), { correo_electronico: 'omar@gmail.com', contrasena: '1234' });
});

test('AUT-F02 Formulario: datos inválidos o incompletos, la validación impide el envío y muestra el error', () => {
  const casos = [
    { email: '', pass: '', alerta: 'alerta1' },
    { email: '', pass: '1234', alerta: 'alerta2' },
    { email: 'correo-invalido', pass: '1234', alerta: 'alerta3' },
    { email: 'omar@gmail.com', pass: '', alerta: 'alerta4' },
  ];
  for (const c of casos) {
    const w = pagina(LOGIN, ['login.js']);
    w.document.getElementById('exampleInputEmail1').value = c.email;
    w.document.getElementById('exampleInputPassword1').value = c.pass;

    w.validarLogin();

    assert.strictEqual(w.__ajax.length, 0, `no debe enviarse la petición (${JSON.stringify(c)})`);
    assert.deepStrictEqual(w.__toasts, [c.alerta], `debe mostrarse ${c.alerta}`);
  }
});
