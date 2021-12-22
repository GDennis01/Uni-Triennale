import java.util.*;

public class SymbolTable {

     Map <String, Integer> OffsetMap = new HashMap <String,Integer>();

	public void insert( String s, int address ) {
            if( !OffsetMap.containsValue(address) ) 
                OffsetMap.put(s,address);
            else 
                throw new IllegalArgumentException("Reference to a memory location already occupied by another variable");
	}

	public int lookupAddress ( String s ) {
            if( OffsetMap.containsKey(s) ) 
                return OffsetMap.get(s);
            else
                return -1;
	}
}
