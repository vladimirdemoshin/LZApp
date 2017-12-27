package lzapplication;

import java.util.ArrayList;

public class LZ78 {
    
    public ArrayList<String> dictionary = new ArrayList<String>();
    
    public String Encrypt(String input)
    {  
       String code = "";
       int position = 0;
       String currentSubstring = "";
       
       for(int i=0;i<input.length();i++)
       {
           char nextSymbol = input.charAt(i);
           currentSubstring += nextSymbol;
           
           String pattern = "^" + currentSubstring;
           boolean find = false;
           
           for(int j = 0;j < dictionary.size();j++)
           {
               if(dictionary.get(j).matches(pattern))
               {
                   position = j+1;
                   if(i+1 == input.length()) 
                       code += position + ' ';
                   find = true;
                   break;
               } 
           }
           
           if(!find)
           {
               dictionary.add(currentSubstring);
               
               code += position;
               code += nextSymbol;
               currentSubstring = "";
               position = 0;
           }
           
       }
       return code;
    }
    
    public String Decrypt(String code)
    {
       String message = "";
       ArrayList<String> dictionary = new ArrayList<String>();
            

            int i = 0;
            while(i<code.length())
            {
                String sPosition = "";
                while(Character.isDigit(code.charAt(i)))
                {
                    sPosition += code.charAt(i);
                    i++;
                }
                int position = Integer.parseInt(sPosition);
                char symbol = code.charAt(i);
                i++;

                String newString = "";
                if (position != 0)
                {
                    message += dictionary.get(position-1);
                    newString += dictionary.get(position-1);
                }
                message += symbol;
                newString += symbol;
                dictionary.add(newString);
            }

            return message;
    }
}

























