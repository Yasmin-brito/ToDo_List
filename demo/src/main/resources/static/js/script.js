const form = document.getElementById("task-form");
const input = document.getElementById("description");
const result = document.getElementById("task-body-result-list");

addTasktoList();
form.addEventListener("submit", function(event){
    event.preventDefault();

    const task = {
        description: input.value
    };

    fetch("http://localhost:8080/tasks", {
        method: "POST",
        headers: {
            "Content-Type":"application/json"
        },
        body: JSON.stringify(task)
    })
    .then(response => response.json())
    .then(data => {
        addTasktoList();
        input.value = "";
    })
    .catch(error => console.error('Erro: ', error));
});


async function addTasktoList() {
   try {
        const response = await fetch("http://localhost:8080/tasks")
        if(!response.ok){
            throw new Error(`Erro na rede: ${response.status}`)
        }

        const datas = await response.json();
        datas.forEach(element => {
            const li = document.createElement("li");
            li.textContent = element.description;

            //marcar como concluida
            if(element.completed == "concluido") {
                li.style.textDecoration = "line-through";
            }
            result.appendChild(li);
        });
   } catch (error){
    console.error('Erro: ', error);
   }
}