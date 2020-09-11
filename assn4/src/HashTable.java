// QuadraticProbing Hash table class
//
// CONSTRUCTION: an approximate initial size or default of 101
//
// ******************PUBLIC OPERATIONS*********************
// bool insert( x )       --> Insert x
// bool remove( x )       --> Remove x
// bool contains( x )     --> Return true if x is present
// void makeEmpty( )      --> Remove all items
import java.util.ArrayList;

/**
 * Probing table implementation of hash tables.
 * Note that all "matching" is based on the equals method.
 * @author Mark Allen Weiss
 */
public class HashTable<D, E>
{
    /**
     * Construct the hash table.
     */

    public HashTable( )
    {
        this( DEFAULT_TABLE_SIZE );
        this.keyList = new ArrayList<>();
    }

    /**
     * Construct the hash table.
     * @param size the approximate initial size.
     */
    public HashTable( int size )
    {
        allocateArray( size );
        doClear( );
        this.keyList = new ArrayList<>();
    }

    /**
     * Insert into the hash table. If the item is
     * already present, do nothing.
     * Implementation issue: This routine doesn't allow you to use a lazily deleted location.  Do you see why?
     * @param x the item to insert.
     */
    public boolean insert(D key, E x)
    {
        // Insert x as active
        int currentPos = findPos(key);
        if( isActive( currentPos ) )
            return false;

        array[currentPos] = new HashEntry<>(x, true);
        currentActiveEntries++;

        keyList.add(key);

        // Rehash; see Section 5.5
        if(++occupiedCt > array.length / 2)
            rehash( );

        return true;
    }

    public String toString (int limit){
        StringBuilder sb = new StringBuilder();
        int ct=0;
        for (int i=0; i < array.length && ct < limit; i++){
            if (array[i]!=null && array[i].isActive) {
                sb.append( "Position: " + i + ": " + array[i].element + "\n" );
                ct++;
            }
        }
        return sb.toString();
    }

    /**
     * Expand the hash table.
     */
    private void rehash( )
    {
        HashTable<D, E> newTable = new HashTable<>(this.array.length);
        newTable.array = array;
        newTable.keyList = this.keyList;

        // Create a new double-sized, empty table
        allocateArray( 2 * newTable.array.length );
        occupiedCt = 0;
        currentActiveEntries = 0;

        // Copy table over
        for (int i = 0; i < keyList.size(); i++) {
            insert(keyList.get(i), newTable.find(keyList.get(i)));
        }
    }

    /**
     * Method that performs quadratic probing resolution.
     * @param x the item to search for.
     * @return the position where the search terminates.
     * Never returns an inactive location.
     */
    private int findPos(D x)
    {
        int offset = 1;
        int currentPos = myhash(x);

        while( array[ currentPos ] != null &&
                !(((WordFreqInfo)array[currentPos].element).word.equals(x)))
        {
            currentPos += offset;  // Compute ith probe
            offset += 2;
            if( currentPos >= array.length )
                currentPos -= array.length;
        }

        return currentPos;
    }

    /**
     * Remove from the hash table.
     * @param x the item to remove.
     * @return true if item removed
     */
    public boolean remove(D x)
    {
        int currentPos = findPos(x);
        if( isActive( currentPos ) )
        {
            array[ currentPos ].isActive = false;
            currentActiveEntries--;
            keyList.remove(x);
            return true;
        }
        else
            return false;
    }

    /**
     * Get current size.
     * @return the size.
     */
    public int size( )
    {
        return currentActiveEntries;
    }

    /**
     * Get length of internal table.
     * @return the size.
     */
    public int capacity( )
    {
        return array.length;
    }

    /**
     * Find an item in the hash table.
     * @param x the item to search for.
     * @return true if item is found
     */
    public boolean contains(D x)
    {
        int currentPos = findPos(x);
        return isActive(currentPos);
    }

    /**
     * Find an item in the hash table.
     * @param x the item to search for.
     * @return the matching item.
     */
    public E find(D x)
    {
        int currentPos = findPos(x);
        if (!isActive( currentPos )) {
            return null;
        }
        else {
            return array[currentPos].element;
        }
    }

    /**
     * Return true if currentPos exists and is active.
     * @param currentPos the result of a call to findPos.
     * @return true if currentPos is active.
     */
    private boolean isActive( int currentPos )
    {
        return array[currentPos] != null && array[currentPos].isActive;
    }

    /**
     * Make the hash table logically empty.
     */
    public void makeEmpty( )
    {
        doClear( );
    }

    private void doClear( )
    {
        occupiedCt = 0;
        for( int i = 0; i < array.length; i++ )
            array[ i ] = null;
    }

    private int myhash(D x)
    {
        int hashVal = x.hashCode( );

        hashVal %= array.length;
        if( hashVal < 0 )
            hashVal += array.length;

        return hashVal;
    }

    private static class HashEntry<E>
    {
        public E  element;   // the element
        public boolean isActive;  // false if marked deleted

        public HashEntry( E e )
        {
            this( e, true );
        }

        public HashEntry( E e, boolean i )
        {
            element  = e;
            isActive = i;
        }
    }

    private static final int DEFAULT_TABLE_SIZE = 101;

    private HashEntry<E> [ ] array; // The array of elements
    private int occupiedCt;         // The number of occupied cells: active or deleted
    private int currentActiveEntries; // Current size
    private ArrayList<D> keyList;

    /**
     * Internal method to allocate array.
     * @param arraySize the size of the array.
     */
    private void allocateArray( int arraySize )
    {
        array = new HashEntry[ nextPrime( arraySize ) ];
    }

    /**
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     *
     */
    private static int nextPrime( int n )
    {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime( int n )
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }


    // Simple main
    public static void main( String [ ] args )
 {
        HashTable<String, String> H = new HashTable<>( );


        long startTime = System.currentTimeMillis( );

        final int NUMS = 2000;
        final int GAP  =   37;

        System.out.println( "Checking... " );


        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
            H.insert("" +i, ""+i );
        // Because GAP and NUMS are mutally prime, this inserts all numbers between 0 and 1999
        System.out.println( "H size is: " + H.size( ) );
        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
            if( H.insert( "" +i,""+i ) )
                System.out.println( "ERROR Find fails " + i );

        for( int i = 1; i < NUMS; i+=2 )
        {
            if( H.contains( ""+i ) )
                System.out.println( "ERROR OOPS!!! " +  i  );
        }

        long endTime = System.currentTimeMillis( );

        System.out.println( "Elapsed time: " + (endTime - startTime) );
        System.out.println( "H size is: " + H.size( ) );
        System.out.println( "Array size is: " + H.capacity( ) );
}

}

