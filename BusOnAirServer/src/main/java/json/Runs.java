package json;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "List" )
public class Runs
{
    @XmlElement( name = "runlist" )
    List<String> runlist = new ArrayList<String>();

    public Runs()
    {
    }

    public void add( Run r )
    {
        runlist.add( "/runs/" +  r.getRunId() );
    }

    public void add( domain.Run r )
    {
        runlist.add( "/runs/" + r.getId() );
    }

}
