document.addEventListener("DOMContentLoaded", loadCustomers);

function loadCustomers() {
    const customers = getCustomers();
    const customerTableBody = document.getElementById('customerTableBody');
    customerTableBody.innerHTML = customers.map(customer => createCustomerRow(customer)).join('');
}

function getCustomers() {
    return [
        { id: 1, name: 'John Doe', email: 'john@example.com', phone: '1234567890', gender: 'Male', address: '123 Main St', finance: 50000, interestStatus: 'Interested', salesConsultant: 'Alice' },
        { id: 2, name: 'Jane Doe', email: 'jane@example.com', phone: '0987654321', gender: 'Female', address: '456 Elm St', finance: 75000, interestStatus: 'Not Interested', salesConsultant: 'Bob' },
        { id: 1, name: 'John Doe', email: 'john@example.com', phone: '1234567890', gender: 'Male', address: '123 Main St', finance: 50000, interestStatus: 'Interested', salesConsultant: 'Alice' },
        { id: 2, name: 'Jane Doe', email: 'jane@example.com', phone: '0987654321', gender: 'Female', address: '456 Elm St', finance: 75000, interestStatus: 'Not Interested', salesConsultant: 'Bob' },
        { id: 1, name: 'John Doe', email: 'john@example.com', phone: '1234567890', gender: 'Male', address: '123 Main St', finance: 50000, interestStatus: 'Interested', salesConsultant: 'Alice' },
        { id: 2, name: 'Jane Doe', email: 'jane@example.com', phone: '0987654321', gender: 'Female', address: '456 Elm St', finance: 75000, interestStatus: 'Not Interested', salesConsultant: 'Bob' },
        { id: 1, name: 'John Doe', email: 'john@example.com', phone: '1234567890', gender: 'Male', address: '123 Main St', finance: 50000, interestStatus: 'Interested', salesConsultant: 'Alice' },
        { id: 2, name: 'Jane Doe', email: 'jane@example.com', phone: '0987654321', gender: 'Female', address: '456 Elm St', finance: 75000, interestStatus: 'Not Interested', salesConsultant: 'Bob' },
    ];
}

function createCustomerRow(customer) {
    return `
        <tr>
            <td>${customer.id}</td>
            <td>${customer.name}</td>
            <td>${customer.email}</td>
            <td>${customer.phone}</td>
            <td>${customer.gender}</td>
            <td>${customer.address}</td>
            <td>${customer.finance}</td>
            <td>${customer.interestStatus}</td>
            <td>${customer.salesConsultant}</td>
            <td class="action-icons">
                <i class="fas fa-edit" onclick="showCustomerModal(${customer.id})"></i>
                <i class="fas fa-trash" onclick="deleteCustomer(${customer.id})"></i>
            </td>
        </tr>
    `;
}

function showCustomerModal(id = null) {
    const customer = id ? getCustomers().find(c => c.id === id) : null;
    document.getElementById('customerModalLabel').innerText = id ? 'Edit Customer' : 'Add Customer';
    document.getElementById('customerId').value = customer ? customer.id : '';
    document.getElementById('customerName').value = customer ? customer.name : '';
    document.getElementById('customerEmail').value = customer ? customer.email : '';
    document.getElementById('customerPhone').value = customer ? customer.phone : '';
    document.getElementById('customerGender').value = customer ? customer.gender : '';
    document.getElementById('customerAddress').value = customer ? customer.address : '';
    document.getElementById('customerFinance').value = customer ? customer.finance : '';
    document.getElementById('customerInterestStatus').value = customer ? customer.interestStatus : '';
    document.getElementById('customerSalesConsultant').value = customer ? customer.salesConsultant : '';
    $('#customerModal').modal('show');
}

function saveCustomer(event) {
    event.preventDefault();
    const id = document.getElementById('customerId').value;
    const customer = {
        name: document.getElementById('customerName').value,
        email: document.getElementById('customerEmail').value,
        phone: document.getElementById('customerPhone').value,
        gender: document.getElementById('customerGender').value,
        address: document.getElementById('customerAddress').value,
        finance: document.getElementById('customerFinance').value,
        interestStatus: document.getElementById('customerInterestStatus').value,
        salesConsultant: document.getElementById('customerSalesConsultant').value
    };

    console.log(id ? `Customer ${id} updated:` : 'New customer added:', customer);
    $('#customerModal').modal('hide');
    loadCustomers();
}

function deleteCustomer(id) {
    console.log(`Customer ${id} deleted`);
    loadCustomers();
}

document.getElementById('customerForm').addEventListener('submit', saveCustomer);



