public class HashTable
{
    private int N;
    private double loadingFactor;
    private String[] wordTable;
    private String[] newWordTable;
    int collisions;

    public HashTable(int N)
    {
        this.N = N;
        this.collisions = 0;
        //create table of size N
        wordTable = new String[N];
        for(int i = 0; i < N; i++)
        {
            wordTable[i] = null;
        }
    }

    public int getN()
    {
        return this.N;
    }

    //insert words from text file into hash table
    public void insert(int prob, String word, int n)
    {
        this.loadingFactor = (double) n / (double) N; // #inserted/table size
        if(loadingFactor > .5)
        {
            Rehash(n, N, prob);
        }
        int i = 0;
        int index = ProbHashFn(prob, i, word);
        while(i < N) //only go through less than the table size
        {
            //if an empty space insert, else do probing
            if(wordTable[index] == null)
            {
                wordTable[index] = word;
                break;
            }
            i++;
            index = ProbHashFn(prob, i, word);
        }
    }

    //look through hashtable to find key word
    public boolean spellChecker(int prob, String targetKey)
    {
        int i = 0;
        int index = ProbHashFn(prob, i, targetKey);
        while(i < N)
        {
            if(wordTable[index] == null)
                break;
            if(targetKey.compareTo(wordTable[index]) == 0)
                return true;
            i++;
            index = ProbHashFn(prob, i, targetKey);
        }
        return false;
    }

    //increase table size and rehash words
    public void Rehash(int n, int N, int prob)
    {
        N = calcPrime(N);
        this.N = N;
        String[] newWordTable = new String[n-1];
        int k = 0;
        for(int j = 0; j < wordTable.length; j++)
        {
            while(wordTable[j] != null)
            {
                    newWordTable[k] = wordTable[j];
                    k++;
                    break;
            }
        }
        //new HashTable(N);
        wordTable = new String[N];
        for(int i = 0; i < N; i++)
        {
            wordTable[i] = null;
        }
        //rehash the old words
        for(int j = 0; j < newWordTable.length; j++)
        {
            insert(prob, newWordTable[j], j);
        }
    }

    //has function to calculate indeces based on 1st 3 chars of string, counts collisions
    public int ProbHashFn(int prob, int i, String key)
    {
        //if rehashing, reset collision counter
        if(loadingFactor > .5)
            this.collisions = 0;
        int index = ((int)key.charAt(2)*1 + (int)key.charAt(1)*37 + (int)key.charAt(0)*((37)^2)) % N;
        //linear probing
        if(prob == 1)
        {
            index = (index + i) % N;
            this.collisions = collisions + 1;
        }
        //quadratic probing
        if(prob == 2)
        {
            index = (index + i * i) % N;
            this.collisions = collisions + 1;
        }
        return index;
    }

    //print collisions
    public void Collisions()
    {
        System.out.println(collisions);
    }

    //calculate prime number for new table size
    public int calcPrime(int N)
    {
        int prime;
        prime = 2*N + 1;
        return prime; //returns prime number twice the size of the original table size
    }
}
