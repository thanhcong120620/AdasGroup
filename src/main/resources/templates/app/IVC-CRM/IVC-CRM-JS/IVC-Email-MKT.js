
    document.addEventListener('DOMContentLoaded', () => {
    const customerTableBody = document.getElementById('customerTableBody');
    const emailForm = document.getElementById('emailForm');
    const addCustomerBtn = document.getElementById('addCustomerBtn');
    const customerModal = new bootstrap.Modal(document.getElementById('customerModal'));
    const customerForm = document.getElementById('customerForm');
    let editIndex = null;
    let customers = [];

    function fetchCustomers() {
        fetch('/api/customers')
            .then(response => response.json())
            .then(data => {
                customers = data;
                renderCustomers();
            })
            .catch(error => console.error('Error fetching customers:', error));
    }

    function renderCustomers() {
        customerTableBody.innerHTML = '';
        customers.forEach((customer, index) => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${customer.name}</td>
                <td>${customer.email}</td>
                <td>${customer.gender}</td>
                <td>
                    <button class="btn btn-primary btn-sm" onclick="editCustomer(${index})"><i class="fas fa-edit"></i></button>
                    <button class="btn btn-danger btn-sm" onclick="deleteCustomer(${index})"><i class="fas fa-trash"></i></button>
                </td>
            `;
            customerTableBody.appendChild(tr);
        });
    }

    addCustomerBtn.addEventListener('click', () => {
        editIndex = null;
        customerForm.reset();
        customerModal.show();
    });

    customerForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const customer = {
            name: document.getElementById('customerName').value,
            email: document.getElementById('customerEmail').value,
            gender: document.getElementById('customerGender').value,
        };
        if (editIndex !== null) {
            customers[editIndex] = customer;
        } else {
            customers.push(customer);
        }
        renderCustomers();
        customerModal.hide();
    });

    emailForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const subject = document.getElementById('emailSubject').value;
        const content = document.getElementById('emailContent').value;
        const emailList = customers.map(c => c.email).join(', ');
        alert(`Sending email to: ${emailList}\nSubject: ${subject}\nContent: ${content}`);
        // Here you can implement the email sending logic
    });

    window.editCustomer = (index) => {
        editIndex = index;
        const customer = customers[index];
        document.getElementById('customerName').value = customer.name;
        document.getElementById('customerEmail').value = customer.email;
        document.getElementById('customerGender').value = customer.gender;
        customerModal.show();
    };

    window.deleteCustomer = (index) => {
        customers.splice(index, 1);
        renderCustomers();
    };

    fetchCustomers();
});
    


