document.addEventListener('DOMContentLoaded', () => {
    // Save changes functions for each section
    const saveProfileChanges = document.getElementById('saveProfileChanges');
    const savePersonalInfoChanges = document.getElementById('savePersonalInfoChanges');
    const saveNeedsChanges = document.getElementById('saveNeedsChanges');
    const savePortraitChanges = document.getElementById('savePortraitChanges');

    saveProfileChanges.addEventListener('click', () => {
        const name = document.getElementById('profileName').value;
        const salesConsultant = document.getElementById('profileSalesConsultant').value;
        document.getElementById('customerName').textContent = name;
        document.getElementById('customerSalesConsultant').textContent = `Sales Consultant: ${salesConsultant}`;
        $('#editProfileModal').modal('hide');
    });

    savePersonalInfoChanges.addEventListener('click', () => {
        const email = document.getElementById('personalEmail').value;
        const phone = document.getElementById('personalPhone').value;
        const gender = document.getElementById('personalGender').value;
        const address = document.getElementById('personalAddress').value;
        const finance = document.getElementById('personalFinance').value;
        const interestStatus = document.getElementById('personalInterestStatus').value;
        document.getElementById('customerEmail').textContent = email;
        document.getElementById('customerPhone').textContent = phone;
        document.getElementById('customerGender').textContent = gender;
        document.getElementById('customerAddress').textContent = address;
        document.getElementById('customerFinance').textContent = finance;
        document.getElementById('customerInterestStatus').textContent = interestStatus;
        $('#editPersonalInfoModal').modal('hide');
    });

    saveNeedsChanges.addEventListener('click', () => {
        const needs = document.getElementById('customerNeedsInput').value;
        document.getElementById('customerNeeds').textContent = needs;
        $('#editNeedsModal').modal('hide');
    });

    savePortraitChanges.addEventListener('click', () => {
        const portrait = document.getElementById('customerPortraitInput').value;
        document.getElementById('customerPortrait').textContent = portrait;
        $('#editPortraitModal').modal('hide');
    });
});
