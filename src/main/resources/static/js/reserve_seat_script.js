$(document).ready(function () {
    $('.seat-content').on('click', function () {
        var seatNumber = $(this).data('seat-number');
        $('#selectedSeat').val(seatNumber); // Update the hidden input field with the selected seat number
    });

    // Handle reservation confirmation button click
    $('#confirmReservation').on('click', function () {
        // Extract cinemaId from the URL query parameters
        var urlParams = new URLSearchParams(window.location.search);
        var cinemaId = urlParams.get('cinemaId');

        // Get the seat number from the hidden input field
        var seatNumber = $('#selectedSeat').val();

        // Make a POST request to reserve the seat
        $.ajax({
            type: 'POST',
            url: '/cinemas/seats/reserve',
            data: { cinemaId: cinemaId, seatNumber: seatNumber },
            success: function (response) {
                // Handle success response (e.g., show a success message)
                console.log('Seat reserved successfully:', response);
            },
            error: function (error) {
                // Handle error response (e.g., show an error message)
                console.error('Error reserving seat:', error);
            }
        });

        // Close the modal when the reservation is confirmed
        $('#reservationModal').modal('hide');
    });
});
