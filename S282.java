/**
 * @author Aaron G
 * @discord meow2980
 * @version 0.1           (first one, I expect updates)
 * @date 5/10/25
 * @build 99.4            (latest build of DV since last update)
 */
public class S282 extends Locomotive//a and b
{
    private final static double MASS=124.8+50;//tons  (+ indicates tender)
    private final static int MASS_EMPTY=111+10;//tons (not used) (+ indicates tender)
    private final static int LENGTH_MM=13640+8540;//milimeters (not used)  (+ indicates tender)
    private final static double LENGTH=13.64+8.54;//meters      (+ indicates tender)
    private final static int FLAT=3000;//0% gradient (tons)
    private final static int UPHILL=1000;//2% gradient (tons)
    private final static int UPHILL_RAIN=800;//2% gradient with rain (tons)
    /**
     * Constructor for objects of class S282
     */
    public S282()
    {
        super(MASS,LENGTH,FLAT,UPHILL,UPHILL_RAIN);
    }
}
