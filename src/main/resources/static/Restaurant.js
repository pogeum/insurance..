var page - 1;
var size - 120;
var right = document.querySelector('.right');
right.addEventListener("click",() => {
    page += 1;
    var carousel = document.querySelector(.list);
    var slideWidth = (size * -1 * (page -1));
    carousel.style.transform = `translateX(${slideWidth}px)`;
})

var right = document.querySelector('.left');
right.addEventListener("click",() => {
    page += 1;
    var carousel = document.querySelector(.list);
    var slideWidth = (size * -1 * (page -1));
    carousel.style.transform = `translateX(${slideWidth}px)`;
})