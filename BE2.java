/**
 * @discord meow2980
 * @version 0.1           (first one, I expect updates)
 * @date 5/10/25
 * @build 99.4            (latest build of DV since last update)
 */
public class BE2 extends WorkTrains //microshunter
{
    private final static int MASS=12;//tons
    private final static double MASS_EMPTY=11.6;//tons (not used)
    private final static int LENGTH_MM=4080;//milimeters (not used)
    private final static double LENGTH=4.08;//meters
    private final static int FLAT=800;//0% gradient (tons)
    private final static int UPHILL=100;//2% gradient (tons)
    private final static int UPHILL_RAIN=50;//2% gradient with rain (tons)
    /**
     * Constructor for objects of class BE2
     */
    public BE2()
    {
        super(MASS,LENGTH,FLAT,UPHILL,UPHILL_RAIN);
    }
}
