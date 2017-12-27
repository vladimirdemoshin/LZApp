package lzapplication;

import java.util.ArrayList;

public class LZ77 {
    
    private class Tuple
    {
        int offset;
        int length;
        char nextSymbol;
        
        Tuple(int offset,int length,char nextSymbol)
        {
            this.offset = offset;
            this.length = length;
            this.nextSymbol = nextSymbol;
        }
    }
    
    
     public String Encrypt(String input)
    {
        String searchBuffer = ""; //в начале буфер поиска пустой
        ArrayList<Tuple> output = new ArrayList<Tuple>(); //создадим список всех выходных троек
        
        for(int i=0;i<input.length();) //проходим по всему входному слову
        {
            int offset = 0;
            int length = 0;
            char nextSymbol = input.charAt(i);
            
            String currentSubstring = ""; //формируем текущюю подстроку
            currentSubstring += input.charAt(i);
            
            String regex = ".*"+currentSubstring+".*"; //создаем регулярное выражения для поиска подстрок
            
            while(searchBuffer.matches(regex)) //пока не найдем уникальную подстроку, увеличиваем подстроку на один символ
            {
                offset = i - searchBuffer.lastIndexOf(currentSubstring); //запоминаем сдвиг (считаем сдвиг справа налево от текущего символа)
                length = currentSubstring.length(); //запоминаем длину крайней неуникальной подстроки
                nextSymbol = i+length >= input.length() ? ' ' : input.charAt(i+length);
                /*если исходная строка оканчивается на неуникальную подстроку,сохраняем в конце пустой символ,
                     иначе запоминаем следующий за подстрокой символ*/
                
                currentSubstring += nextSymbol; //увеличиваем текущую подстроку
                regex = ".*"+currentSubstring+".*"; //обновляем наше регулярное выражение
            }
            
            searchBuffer += currentSubstring; //добавляем в буфер уникальную подстроку
            
            output.add(new Tuple(offset,length,nextSymbol)); //формируем очередную выходную тройку
            
            i += length + 1; //переходим к первому символу новой подстроки
        }
        
        String result = "";
        
        for(Tuple t : output)
        {
            result += t.offset;
            result += ':';
            result += t.length;
            result += t.nextSymbol;
        }
        
        return result;
    }
     
     public String Decrypt(String cipher)
     {
         String message = "";
            for (int i = 0; i < cipher.length(); )
            {
                String stringShift = "";
                while (cipher.charAt(i) != ':') { stringShift += cipher.charAt(i); i++; }
                int shift = Integer.parseInt(stringShift);
                i++;

                String stringLength = "";
                while (Character.isDigit(cipher.charAt(i))) { stringLength += cipher.charAt(i); i++; }
                int length = Integer.parseInt(stringLength);
                

                char symbol = '\0';
                if (i != cipher.length())
                {
                    symbol = cipher.charAt(i);
                }
                
                i++;

                int currentLength = message.length();
                for (int j = currentLength - shift; j < currentLength - shift + length; j++)
                {
                    message += message.charAt(j);
                }

                if(symbol!='\0')
                    message += symbol;
                
            }
            return message;
     }
}
