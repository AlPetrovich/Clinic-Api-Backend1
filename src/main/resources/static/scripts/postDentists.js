window.onload = () => {
    const formulario = document.getElementById("formularioOdontologo");

    formulario.addEventListener("submit", event => {
        event.preventDefault();
        agregarOdontologo();
    });
}

function agregarOdontologo() {
    const name = document.getElementById("name");
    const lastname = document.getElementById("lastname");
    const dni = document.getElementById("dni");
    const licence = document.getElementById("licence");

    const formData = {
      name: name.value,
      lastname: lastname.value,
      dni: dni.value,
      licence: licence.value,
    };

    const settings = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
    };

    fetch("http://localhost:8080/api/dentist", settings)
      .then((response) => response.json())
      .then(() => alert("Se creó el odontólogo"))
      .catch(() => alert("No se pudo crear el odontólogo"))
      .finally(() => {
          name.value = "";
          lastname.value = "";
          dni.value = "";
          licence.value = "";
      })
}