    document.getElementById("contactForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent form submission

    // Get form values
    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const phone = document.getElementById("phone").value.trim();
    const message = document.getElementById("message").value.trim();

    // Get error message elements
    const nameError = document.getElementById("nameError");
    const emailError = document.getElementById("emailError");
    const phoneError = document.getElementById("phoneError");
    const messageError = document.getElementById("messageError");

    // Clear previous errors
    clearErrors();

    // Validation patterns
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const phonePattern = /^\d{10}$/;

    let isValid = true;

    // Validate name
    if (!name) {
        showError(nameError, "Name is required.");
        isValid = false;
    }

    // Validate email
    if (!emailPattern.test(email)) {
        showError(emailError, "Invalid email address.");
        isValid = false;
    }

    // Validate phone
    if (!phonePattern.test(phone)) {
        showError(phoneError, "Enter a valid phone number.");
        isValid = false;
    }

    // Validate message
    if (!message) {
        showError(messageError, "Message cannot be empty.");
        isValid = false;
    }

    // If valid, submit the form (or show a success message)
    if (isValid) {
        alert("Form submitted successfully!");
    }

    // Helper functions
    function showError(element, message) {
        element.style.display = "block";
        element.innerText = message;
    }

    function clearErrors() {
        nameError.style.display = "none";
        emailError.style.display = "none";
        phoneError.style.display = "none";
        messageError.style.display = "none";
    }
});
