
class inputValidator{
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
}