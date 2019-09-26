    package ai;
    
    public class CoOrd 
    {
        public int first; 
        public int last; 
    
        public CoOrd(int a, int b)
        {
            this.first=a;
            this.last=b;
        }

        @Override
    public boolean equals(Object o) { 
  
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of CoOrd or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof CoOrd)) { 
            return false; 
        } 
          
        // typecast o to CoOrd so that we can compare data members  
        CoOrd c = (CoOrd) o; 
          
        if (c.first == this.first && c.last == this.last)
        {
            return true;
        }
        return false;
    } 
}  
    