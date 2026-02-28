const form = document.getElementById("task-form");
const descriptionInput = document.getElementById("description");
const taskList = document.getElementById("task-body-result-list");

addTasktoList();
form.addEventListener("submit", function(event){
    event.preventDefault();

    const task = {
        description: descriptionInput.value
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
        descriptionInput.value = "";
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
            const checkBox = document.createElement("input")
            checkBox.type = "checkbox";
            checkBox.id = `checkbox-${element.id}`;

            const label = document.createElement("label");
            label.setAttribute("for", checkBox.id);

            const span = document.createElement("span");
            span.textContent = element.description;

            const btnIconPen = document.createElement("button");
            const btnIconTrash = document.createElement("button");
            btnIconTrash.innerHTML = '<i class="fi fi-rr-trash-xmark"></i>'
            btnIconPen.innerHTML = '<i class="fi fi-rr-pencil"></i>'
            btnIconPen.className = "btn-icon-pen";
            btnIconTrash.className = "btn-icon-trash";

            btnIconTrash.addEventListener("click", async () =>{
                const confirmDelete = confirm("Deseja realmente excluir esta task?");
                if (!confirmDelete) return;

                try {
                    await fetch(`http://localhost:8080/tasks/${element.id}`, {
                        method: "DELETE"
                    });
                    li.remove();

                } catch (error) {
                    console.error("Erro ao deletar:", error);
                }

            })
            
            label.appendChild(span)
            li.appendChild(checkBox);
            li.appendChild(label);
            li.appendChild(btnIconPen)
            li.appendChild(btnIconTrash)

            if(element.status == "CONCLUIDO") {
                checkBox.checked = true;
            }
            Boxchecked(checkBox, li, element, span);
            taskList.appendChild(li);
        });
   } catch (error){
    console.error('Erro: ', error);
   }
}
function Boxchecked(checkBox, li, element, span){
    checkBox.addEventListener("change", async () => {
        const statusCheckBox = checkBox.checked ? "CONCLUIDA" : "PENDENTE";
        span.style.textDecoration = checkBox.checked ? "line-through" : "none";
        try {
            await fetch(`http://localhost:8080/tasks/${element.id}/status`, {
                method: "PATCH",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    status: statusCheckBox
                })
            });

        }catch (error) {
            console.error("Erro:", error);
        }
    });
}

function clickIconPen() {
    btnPen.addEventListener("click", async () => {

    })
}