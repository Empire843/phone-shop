const slideContainer = document.querySelector('#slide-container');
const prevBtn = document.getElementById('prevBtn');
const nextBtn = document.getElementById('nextBtn');

let counter = 0;
const size = slideContainer.children[0].clientWidth;

nextBtn.addEventListener('click', () => {
    if (counter >= slideContainer.children.length - 1) return;
    counter++;
    slideContainer.style.transform = 'translateX(' + (-size * counter) + 'px)';
});

prevBtn.addEventListener('click', () => {
    if (counter <= 0) return;
    counter--;
    slideContainer.style.transform = 'translateX(' + (-size * counter) + 'px)';
});