package org.omnifaces.elios.config.data;

import java.lang.reflect.Constructor;
import java.util.Map;

import javax.security.auth.message.AuthException;
import javax.security.auth.message.MessagePolicy;

/**
 * Class representing a single AuthModule entry configured for an ID, interception point, and stack.
 *
 * <p>
 * This class also provides a way for a caller to obtain an instance of the module listed in the entry by invoking the
 * <code>newInstance</code> method.
 */
public class AuthModuleBaseConfig {

    // for loading modules
    private static final Class[] PARAMS = {};
    private static final Object[] ARGS = {};

    public String moduleClassName;
    public MessagePolicy requestPolicy;
    public MessagePolicy responsePolicy;
    public Map options;

    /**
     * Construct a ConfigFile entry.
     *
     * <p>
     * An entry encapsulates a single module and its related information.
     *
     * @param moduleClassName the module class name
     * @param requestPolicy the request policy assigned to the module listed in this entry, which may be null.
     *
     * @param responsePolicy the response policy assigned to the module listed in this entry, which may be null.
     *
     * @param options the options configured for this module.
     */
    public AuthModuleBaseConfig(String moduleClassName, MessagePolicy requestPolicy, MessagePolicy responsePolicy, Map options) {
        this.moduleClassName = moduleClassName;
        this.requestPolicy = requestPolicy;
        this.responsePolicy = responsePolicy;
        this.options = options;
    }

    /**
     * Return the request policy assigned to this module.
     *
     * @return the policy, which may be null.
     */
    public MessagePolicy getRequestPolicy() {
        return requestPolicy;
    }

    /**
     * Return the response policy assigned to this module.
     *
     * @return the policy, which may be null.
     */
    public MessagePolicy getResponsePolicy() {
        return responsePolicy;
    }

    public String getModuleClassName() {
        return moduleClassName;
    }

    public Map getOptions() {
        return options;
    }

    /**
     * Return a new instance of the module contained in this entry.
     *
     * <p>
     * The default implementation of this method attempts to invoke the default no-args constructor of the module class.
     * This method may be overridden if a different constructor should be invoked.
     *
     * @return a new instance of the module contained in this entry.
     *
     * @exception AuthException if the instantiation failed.
     */
    public Object newInstance() throws AuthException {
        try {
            final ClassLoader finalLoader = null; // tmp
            Class c = Class.forName(moduleClassName, true, finalLoader);
            Constructor constructor = c.getConstructor(PARAMS);
            return constructor.newInstance(ARGS);
        } catch (Exception e) {

            AuthException ae = new AuthException();
            ae.initCause(e);
            throw ae;
        }
    }
}