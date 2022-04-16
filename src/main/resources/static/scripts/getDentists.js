window.onload = () => {
  fetch("http://localhost:8080/api/dentist/list")
      .then((response) => response.json())
      .then((data) => cargarInformacionOdontologos(data));
};

function cargarInformacionOdontologos(odontologos) {
  const tablaOdontologos = document
    .getElementById("tablaOdontologos")
    .getElementsByTagName("tbody")[0];

    odontologos.forEach((dentist) => {
      tablaOdontologos.innerHTML += `
        <tr>
            <td>${dentist.id}</td>
            <td>${dentist.name}</td>
            <td>${dentist.lastname}</td>
            <td>${dentist.licence}</td>
            <td><button onClick="actualizarOdontologo(${dentist.id})">Update</button></td>
            <td><button onClick="eliminarOdontologo(${dentist.id})">Delete</button></td>
        </tr>
    `;
    });
}