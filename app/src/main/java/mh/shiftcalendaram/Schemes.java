package mh.shiftcalendaram;

import java.util.ArrayList;

/**
 * Created by Martin on 11.08.2016.
 */
public class Schemes {

    String title;
    String firstShift;
    String secondShift;
    String thirdShift;
    String forthShift;
    int numberOfShifts;
    static ArrayList<Schemes> listOfShifts;

    private Schemes(String title, String firstShift, String secondShift)
    {
        this.title = title;
        this.firstShift = firstShift;
        this.secondShift = secondShift;
        numberOfShifts = 2;
    }

    private Schemes(String title,String firstShift, String secondShift, String thirdShift)
    {
        this.title = title;
        this.firstShift = firstShift;
        this.secondShift = secondShift;
        this.thirdShift = thirdShift;
        numberOfShifts = 3;
    }

    private Schemes(String title, String firstShift, String secondShift, String thirdShift, String forthShift)
    {
        this.title = title;
        this.firstShift = firstShift;
        this.secondShift = secondShift;
        this.thirdShift = thirdShift;
        this.forthShift = forthShift;
        numberOfShifts = 4;
    }

    public int getNumberOfShits()
    {
        return numberOfShifts;
    }

    public static ArrayList<Schemes> createList()
    {
        listOfShifts = new ArrayList<Schemes>();
        listOfShifts.add(new Schemes("0058-0088", "ONNVVRRO", "NVVRROON", "VRROONNV", "ROONNVVR"));
        listOfShifts.add(new Schemes("0018-0048", "ORRVVNNO", "RVVNNOOR", "VNNOORRV", "NOORRVVN"));
        listOfShifts.add(new Schemes("0428-0458", "NOOVVRRN", "OVVRRNNO", "VRRNNOOV", "RNNOOVVR"));
        listOfShifts.add(new Schemes("0858-0888, 5858-5888, 0958-0988, 5958-5988", "VVRN", "RNVV", "VRNV", "NVVR"));
        listOfShifts.add(new Schemes("0918-0948, 5918-5948", "VNNVVRRV", "RVVNNVVR", "VRRVVNNV", "NVVRRVVN"));
        listOfShifts.add(new Schemes("0318-0338", "OVVRRO", "VRROOV", "ROOVVR"));
        listOfShifts.add(new Schemes("3318-3338", "VOOOOOOVVRRRRRRVVNNNNNVV", "VNNNNNVVVOOOOOOVVRRRRRRV", "VRRRRRRVVNNNNNVVVOOOOOOV"));
        listOfShifts.add(new Schemes("0218-0238", "RVVRRR", "VRRRRV", "RRRVVR"));
        listOfShifts.add(new Schemes("0268-0288", "OVVRRO", "VRROOV", "ROOVVR"));
        listOfShifts.add(new Schemes("0818-0828", "VVVRRR", "VVVNNN"));
        listOfShifts.add(new Schemes("0618-0648", "VVVNNNVVVRRR", "VVVRRRVVVNNN", "NNNVVVRRRVVV", "RRRVVVNNNVVV"));
        listOfShifts.add(new Schemes("0838-0848", "RVVR", "VRRV"));
        listOfShifts.add(new Schemes("0368-0348", "RVVOOR", "VOORRV", "ORRVVO"));
        listOfShifts.add(new Schemes("7438-7458", "OOOVVRRRRRVVNNNNNVVOO","NNNVVOOOOOVVRRRRRVVNN" ,"RRRVVNNNNNVVOOOOOVVRR"));
        listOfShifts.add(new Schemes("AMFM", "DDOOONNVVDDDOONNVVVDDOONNKVV", "OONNKVVDDOOONNVVDDDOONNVVVDD", "NNVVVDDOONNKVVDDOOONNVVDDDOO", "VVDDDOONNVVVDDOONNKVVDDOOONN"));
        listOfShifts.add(new Schemes("0918-5948", "VVVVDDNN", "VVRRNNVV", "DDNNVVVV", "NNVVVVDD"));

        return listOfShifts;

    }

    public String getTitle()
    {
        return title;
    }

    public String getShiftA()
    {
        return firstShift;
    }

    public String getShiftB()
    {
        return secondShift;
    }

    public String getShiftC()
    {
        return thirdShift;
    }

    public String getShiftD()
    {
        return forthShift;
    }



    public static ArrayList<String> getStringArray()
    {
        ArrayList<Schemes> pernament = Schemes.createList();
        ArrayList<String> getString = new ArrayList<String>();
        for(int i = 0; i < pernament.size();i++)
        {
            getString.add(pernament.get(i).getTitle());
        }
        return getString;
    }

    public String getABCDShifts(String radio)
    {
        String shiftScheme = null;
        switch(radio)
        {
            case "A": shiftScheme = getShiftA(); break;
            case "B": shiftScheme = getShiftB(); break;
            case "C": shiftScheme = getShiftC(); break;
            case "D": shiftScheme = getShiftD(); break;
        }

        return shiftScheme;
    }
}
