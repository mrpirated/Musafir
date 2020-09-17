//Deepesh Manoj Rathi 20194066
import java.util.*;

public class A4Q5
{
	public static int compare(String s1, String s2) 
    { 
  
        int l1 = s1.length(); 
        int l2 = s2.length(); 
        int lmin = Math.min(l1, l2); 
  
        for (int i = 0; i < lmin; i++)
        { 
            int c1 = (int)s1.charAt(i); 
            int c2 = (int)s2.charAt(i); 
  
            if (c1 != c2)
                return c1 - c2; 
        } 
   
        if (l1 != l2) 
            return l1 - l2;

        else
            return 0;
    }

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		String p, c;
		System.out.print("Enter the string: ");
		p = sc.nextLine();
		System.out.print("Enter the character at which string should break: ");
		c= sc.nextLine();
		StringTokenizer st = new StringTokenizer(p,c);
		String s[] = new String[100];
		int i=0, k=0, j=0;
		while(st.hasMoreTokens())
		{
			s[i]=st.nextToken();
			i++;
		}
		String t;



		
		for(k=0; k<i; k++)
		{
			for(j=0; j<i-k-1;j++)
			{
				if(compare(s[j], s[j+1])>0)
				{
					t=s[j];
					s[j]=s[j+1];
					s[j+1]=t;
				}
			}
		}
			

		String a[]=new String [100];
		a[0] = s[0];
		j=1;
		for(k=1; k<i; k++)
		{

			if(compare(s[k-1], s[k])==0)
				continue;
			else
			{
				a[j]=s[k];
				j++;
			}
		}

		System.out.println("The sorted tokens are:");
		for(k=0; k<j; k++)
		System.out.println(a[k]);
			
	}
}