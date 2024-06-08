function formatDate(postDate) {
    const date = new Date(postDate);
    const today = new Date();
    const isToday = date.getDate() === today.getDate() &&
        date.getMonth() === today.getMonth() &&
        date.getFullYear() === today.getFullYear();

    if (isToday) {
        const hours = date.getHours().toString().padStart(2, '0');
        const minutes = date.getMinutes().toString().padStart(2, '0');
        return `${hours}:${minutes}`;
    } else {
        const year = date.getFullYear();
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const day = date.getDate().toString().padStart(2, '0');
        return `${year}.${month}.${day}`;
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const dateElements = document.querySelectorAll('.post-date, .reply-date');
    dateElements.forEach(function (element) {
        const postDate = element.getAttribute('data-post-date');
        element.textContent = formatDate(postDate);
    });
});

