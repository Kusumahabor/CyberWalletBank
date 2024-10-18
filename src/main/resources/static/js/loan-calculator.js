const interestRates = {
    personal: 8.99,
    auto: 4.49,
    home: 3.25,
    business: 6.75
};

function showCalculator(loanType) {
    document.getElementById('loan-calculator').style.display = 'block';
    document.getElementById('loan-type').value = loanType;
    updateInterestRate();
}

document.getElementById('loan-type').addEventListener('change', updateInterestRate);

function updateInterestRate() {
    const loanType = document.getElementById('loan-type').value;
    const interestRate = interestRates[loanType];
    document.getElementById('interest-rate').value = interestRate + '% APR';
}

document.getElementById('calculator-form').addEventListener('submit', function(e) {
    e.preventDefault();
    calculateLoan();
});

function calculateLoan() {
    const loanType = document.getElementById('loan-type').value;
    const amount = parseFloat(document.getElementById('loan-amount').value);
    const term = parseInt(document.getElementById('loan-term').value);
    const rate = interestRates[loanType] / 100 / 12;
    const payments = term * 12;

    if (amount && term) {
        const x = Math.pow(1 + rate, payments);
        const monthly = (amount * x * rate) / (x - 1);

        const totalInterest = (monthly * payments) - amount;
        const totalRepayment = monthly * payments;

        document.getElementById('monthly-payment').textContent = '$' + monthly.toFixed(2);
        document.getElementById('total-interest').textContent = '$' + totalInterest.toFixed(2);
        document.getElementById('total-repayment').textContent = '$' + totalRepayment.toFixed(2);
        document.getElementById('calculation-result').style.display = 'block';
    }
}

// Initialize the calculator
updateInterestRate();

