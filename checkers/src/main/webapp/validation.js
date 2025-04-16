class inputValidator{
    //username validation
    //Requirement [3,4,5]
    static validateUsername(username){
        
        if (!username) 
            return "Username cannot be empty.";
        if (username.length <4 || username.length >20)
            return "Username must be 4-20 characters long.";
        //using regex for input validation
        if (!/^[a-zA-Z0-9_]+$/.test (username))
            return "Username can only contain letters, numbers and underscores.";
        if (/\s/.test(username))
            return "Username cannot contain spaces.";

    }

    //password validation using regex
    //Requirement [6,7]
    static validatePassword(password){
        if (!password)
            return "Password cannot be empty.";
        if (password.length <8) 
            return "Password must be 8 characters long.";
        if (!/ [A-Z]/.test(password))
            return "Password must contain at least one uppecare letter.";
        if (!/[a-z]/.test(password))
            return "Password must contain at least one lowwercase letter.";
        if (!/[0-9]/.test(password))
            return "Password must contain at least one number.";
        if (!/[!@#$%^&*]/.test(password))
            return "Password must contain one special character (! @ # $ % ^ & *).";
        //if (/\s/.test (password))
            //return "Password cannot contain spaces.";
    }

    //email validation
    static validateEmail(email){
        //regex that validates . or - in username, followed by @ and accepts domains with . or -, period and TLD of atleast 2 characters
        //example: john12.abc@mavs.uta
        const regex= /^[\w.-]+@[a-zA-Z\d.-]+\.[a-zA-Z]{2,}$/;
        if (!email)
            return "Email cannot be empty";
        if (!regex.test(email) )
            return "Invalid email format. (eg: john12-abc.az@uta.edu)";
    }



}

