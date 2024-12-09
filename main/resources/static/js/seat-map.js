// seat-map.js
window.onload = function () {
    try {
        const seatRows = JSON.parse(seatRowsJson); // Parse the JSON data passed from the template
        initSeatMap(seatRows); // Initialize the seat map
    } catch (error) {
        console.error("Failed to parse seat rows JSON:", error);
    }
};

// Function to initialize and render the seat map
function initSeatMap(seatRows) {
    const seatMapContainer = document.getElementById("seatMap");

    // Clear existing content (if any)
    seatMapContainer.innerHTML = "";

    // Loop through the rows and create the seat map
    seatRows.forEach(row => {
        const rowDiv = document.createElement("div");
        rowDiv.classList.add("seat-row");

        row.seats.forEach(seat => {
            const seatDiv = document.createElement("div");
            seatDiv.classList.add("seat");

            // Assign seat properties
            seatDiv.innerText = seat.number;
            seatDiv.classList.add(seat.book ? "unavailable" : "available");

            // Add click event for selecting seats
            if (!seat.book) {
                seatDiv.onclick = function () {
                    seatDiv.classList.toggle("selected");
                };
            }

            rowDiv.appendChild(seatDiv);
        });

        seatMapContainer.appendChild(rowDiv);
    });
}
