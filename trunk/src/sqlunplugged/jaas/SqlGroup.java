package sqlunplugged.jaas;

//author: Remi Ham

import java.security.Principal;
import java.security.acl.Group;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;

public class SqlGroup extends SqlPrincipal implements Group
{
	private HashSet members = new HashSet();

	public SqlGroup(String name)
	{
		super(name);
		System.out.println("SqlGroup()");
	}

	public boolean addMember(Principal user)
	{
		System.out.println("SqlGroup.addMember()");
		boolean ret = false;
		if (!isMember(user))
		{
			members.add(user);
			ret = true;
		}
		return ret;
	}

	public boolean isMember(Principal member)
	{
		System.out.println("SqlGroup.isMember()");
		return members.contains(member);
	}

	public Enumeration members()
	{
		System.out.println("SqlGroup.members()");
		return Collections.enumeration(members);
	}

	public boolean removeMember(Principal user)
	{
		System.out.println("SqlGroup.removeMember()");
		boolean ret = false;
		if (isMember(user))
		{
			members.remove(user);
			ret = true;
		}
		return ret;
	}

	public boolean equals(Object o)
	{
		System.out.print("SqlGroup.equals()");
		boolean ret = false;
		if (o != null)
		{
			if (o instanceof SqlGroup)
			{
				SqlGroup other = (SqlGroup)o;
				if (name.equals(other.name))
				{
					if (members.equals(other.members))
					{
						ret = true;
					}
				}
			}
		}
		return ret;
	}

	public int hashCode()
	{
		System.out.println("SqlGroup.hashCode()");
		return members.hashCode();
	}

	public String toString()
	{
		System.out.println("SqlGroup.toString()");
		return super.toString() + ": " + members;
	}
}