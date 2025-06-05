package beehive.event;

abstract class Situation {
    private boolean doneOnce;
    protected int duration;

    public final void execute(){
        if(duration > 0){
            if(!doneOnce){ doOnce(); }

            doContinuously();
            duration--;
        }
    }
    abstract void doOnce();
    abstract void doContinuously();
}
