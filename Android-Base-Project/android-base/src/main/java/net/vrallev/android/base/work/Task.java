package net.vrallev.android.base.work;

/**
 * @author Ralf Wondratschek
 */
public interface Task<Params, Result> {

    public Result execute(Params param);
}
