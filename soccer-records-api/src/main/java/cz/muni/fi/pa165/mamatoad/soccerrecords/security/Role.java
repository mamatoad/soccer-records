package cz.muni.fi.pa165.mamatoad.soccerrecords.security;

/**
 *
 * @author Maros Klimovsky
 */
public enum Role
{
    ADMIN(null),
    
    /* this is the default role, it applies when no other role is specified
     * it can be for example used for user that is not logged in
     */ 
    NONE(ADMIN); 
    
    private final Role parent;
    
    /**
     * 
     * @param parent is a role that inherits all permissions of this role
     */
    Role(Role parent)
    {
        this.parent = parent;
    }
    
    public Role getParentRole()
    {
        return parent;
    }
    
    public boolean hasParent(Role role)
    {
        if(parent == null) return false;
        
        return parent == role || parent.hasParent(role);
    }
}
