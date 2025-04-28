class inputValidator{
    //username validation
    //Requirement [3,4,5]
    static validateUsername(username){
        
        if (!username) 
            return "Username cannot be empty.";
        if (username.length <4 || username.length >20)
            return "Username must be 4-20 characters long.";
        //using regex for input validation
        if (!/^[a-zA-Z0-9_]+$/.test(username))
            return "Username can only contain letters, numbers and underscores.";
        if (/\s/.test(username))
            return "Username cannot contain spaces.";
        return "";
    }

    //password validation using regex
    //Requirement [6,7]
    static validatePassword(password){
        if (!password)
            return "Password cannot be empty.";
        if (password.length <8) 
            return "Password must be at least 8 characters long.";
        if (!/[A-Z]/.test(password))
            return "Password must contain at least one uppercase letter.";
        if (!/[a-z]/.test(password))
            return "Password must contain at least one lowercase letter.";
        if (!/[0-9]/.test(password))
            return "Password must contain at least one number.";
        if (!/[!@#$%^&*]/.test(password))
            return "Password must contain one special character (! @ # $ % ^ & *).";
        return "";
    }

    //email validation
    //Requirement [8]
    static validateEmail(email){
        //regex that validates . or - in username, followed by @ and accepts domains with . or -, period and TLD of atleast 2 characters
        //example: john12.abc@mavs.uta
        const regex= /^[\w.-]+@[a-zA-Z\d.-]+\.[a-zA-Z]{2,}$/;
        if (!email)
            return "Email cannot be empty";
        if (!regex.test(email) )
            return "Invalid email format. (eg: example-123@uta.edu)";
        return "";
    }

    //display error message under the error field related to html
    //Requirement [18-24]
    static displayError(fieldID, message){
        const elementError= document.getElementById (fieldID);
        //if there is an error
        if(elementError)
        {
            elementError.innerText=message; //display error message
            elementError.style.color= "red";// display in red
            

        }
    }

    //clear error once the user tries to resubmit the form/ reset form
    static clearErrors()
    {
        const clearElements= document.querySelectorAll(".error");

        //clear the error msg for each error element
        clearElements.forEach((elementError) => {
            elementError.innerText= "";
        });
    }


}
export {inputValidator};
