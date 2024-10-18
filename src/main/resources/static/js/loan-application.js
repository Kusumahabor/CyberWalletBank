// Wait for the DOM to be fully loaded before attaching event listeners
document.addEventListener('DOMContentLoaded', function() {
    // Get the "Apply Now" button
    const applyNowBtn = document.getElementById('applyNowBtn');

    // Add click event listener to the button
    if (applyNowBtn) {
        applyNowBtn.addEventListener('click', handleApplyNowClick);
    }

    // Function to handle the click event
    function handleApplyNowClick(event) {
        event.preventDefault(); // Prevent any default action
        showThankYouMessage();
    }

    // Function to show the thank you message
    function showThankYouMessage() {
        alert("Thanks for choosing us. Our Executive will contact you further.");
    }
});