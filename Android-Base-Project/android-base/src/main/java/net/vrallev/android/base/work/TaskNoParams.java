package net.vrallev.android.base.work;

/**
 * @author Ralf Wondratschek
 */
public interface TaskNoParams<Result> {

    public Result execute();
}
