console.log("contacts called")

const viewContactModal=document.getElementById('view_contact_modal');
const baseURL='http://localhost:8080'
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

const instanceOptions = {
    id: 'view_contact_modal',
    override: true
};

const contactModal=new Modal(viewContactModal,options,instanceOptions);

function openContactModal(){
    contactModal.show();
}
function closeContactModal(){
    contactModal.hide();
    location.reload();
}

async function loadContactData(id){
    //fetching the data through API:-
    console.log(id);
    try {
        const data= await (await fetch(`${baseURL}/api/contacts/${id}`)).json();
        console.log(data);
        document.getElementById("contact_name").innerHTML=data.name;
        document.getElementById("contact_email").innerHTML=data.email;
        document.getElementById("contact_picture").src=data.picture;
        document.getElementById("contact_phoneNumber").innerHTML=data.phoneNumber;
        document.getElementById("contact_address").innerHTML=data.address;
        document.getElementById("contact_description").innerHTML=data.description;
        if(data.websiteLink){
            document.getElementById("contact_websiteLink").href=data.websiteLink;
            document.getElementById("contact_websiteLink").innerHTML=data.websiteLink;
        }else{
            document.getElementById("contact_websiteLink").innerHTML="Not present";
        }
        if(data.linkedInLink){
            document.getElementById("contact_linkedInLink").href=data.websiteLink;
            document.getElementById("contact_linkedInLink").innerHTML=data.linkedInLink;
        }else{
            document.getElementById("contact_linkedInLink").innerHTML="Not present";
        }
        if(data.favourite == true){
            console.log("favourite");
            document.getElementById("contact_favourite").classList.remove("hidden");
        }
        
        openContactModal();
    } catch (error) {
        console.log(error);
    }

}

// Delete Contacst

async function deleteContact(id){
    Swal.fire({
        title: "Sure you want to delete",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
      }).then((result) => {
        if (result.isConfirmed) {
            const url= `${baseURL}/user/contacts/delete/`+id;
            window.location.replace(url);
          Swal.fire({
            title: "Deleted!",
            text: "Your Contact has been deleted.",
            icon: "success"
          });
        }
      });
}