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
            return "PAssword must contain one special character (! @ # $ % ^ & *).";
        //if (/\s/.test (password))
            //return "Password cannot contain spaces.";
    }

    


}

