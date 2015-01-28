package net.thewaffleshop.jmx;

import java.io.IOException;
import java.net.MalformedURLException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;


/**
 * Goal that calls a method on an mbean
 *
 * @author rhollencamp
 */
@Mojo(name = "rmi")
public class RmiMojo extends AbstractMojo
{
	@Parameter
	private String url;

	@Parameter
	private String objectName;

	@Parameter
	private String method;
	
	public void execute() throws MojoExecutionException
	{
		try {
			JMXServiceURL jmxUrl = new JMXServiceURL(url);
			JMXConnector jmxc = JMXConnectorFactory.connect(jmxUrl, null);
			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
			ObjectName mbeanName = new ObjectName(objectName);
			mbsc.invoke(mbeanName, method, new Object[] {}, new String[] {});
		} catch (MalformedURLException ex) {
			getLog().error(ex);
		} catch (IOException ex) {
			getLog().error(ex);
		} catch (MalformedObjectNameException ex) {
			getLog().error(ex);
		} catch (InstanceNotFoundException ex) {
			getLog().error(ex);
		} catch (MBeanException ex) {
			getLog().error(ex);
		} catch (ReflectionException ex) {
			getLog().error(ex);
		}
	}
}
