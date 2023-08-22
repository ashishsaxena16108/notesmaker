function giveForm(){
    const newNote = document.getElementsByClassName("newNote")[0];
    newNote.style.display=(newNote.style.display==="none")?"block":"none";
}
  function changeColor() {
    const colorSelectElement = document.querySelector("#color");
    const selectedOption = colorSelectElement.options[colorSelectElement.selectedIndex];
    const selectedColor = selectedOption.value;
    colorSelectElement.style.backgroundColor = selectedColor;

    const newNote = document.querySelector(".newNote");
    newNote.style.backgroundColor = selectedColor;
  }
