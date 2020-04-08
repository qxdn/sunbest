package com.qianxu.sunbest.util;

import com.qianxu.sunbest.model.DIFF;
import com.qianxu.sunbest.model.DNR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Slf4j
public class Handle {
        public double p = 0.2;
        public int[] n = {17,47,75,105,135,162,198,228,258,288,318,344};
        public double y_0 = 0.0;
        public double b_0 = 0.0;
        public int[] month = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};

    public ArrayList<Double> getHb(DNR dnr) {//Hb
        ArrayList<Double>	Hb = new ArrayList<Double>();
        Hb.add(dnr.getJAN());
        Hb.add(dnr.getFEB());
        Hb.add(dnr.getMAR());
        Hb.add(dnr.getAPR());
        Hb.add(dnr.getMAY());
        Hb.add(dnr.getJUN());
        Hb.add(dnr.getJUL());
        Hb.add(dnr.getAUG());
        Hb.add(dnr.getSEP());
        Hb.add(dnr.getOCT());
        Hb.add(dnr.getNOV());
        Hb.add(dnr.getDEC());
        return Hb;
    }

    public ArrayList<Double> getHd(DIFF diff) {//Hd
        ArrayList<Double>	Hd = new ArrayList<Double>();
        Hd.add(diff.getJAN());
        Hd.add(diff.getFEB());
        Hd.add(diff.getMAR());
        Hd.add(diff.getAPR());
        Hd.add(diff.getMAY());
        Hd.add(diff.getJUN());
        Hd.add(diff.getJUL());
        Hd.add(diff.getAUG());
        Hd.add(diff.getSEP());
        Hd.add(diff.getOCT());
        Hd.add(diff.getNOV());
        Hd.add(diff.getDEC());
        return Hd;
    }

    public void arrangeData(ArrayList<Double> Hb,ArrayList<Double> Hd,int[] n) {//筛选
        int cnt = 0;
        int j;
        int[] cnt_j = new int[n.length];

        for(int i = 0;i < n.length;i++)
        {
            j = i-cnt;
            if((Hb.get(j) <= 0)||(Hd.get(j) <= 0))
            {
                month[cnt] = i;
                Hb.remove(j);
                Hd.remove(j);
                cnt_j[cnt] = j;
                cnt += 1;
            }
        }
        if(cnt > 0)
        {
            int[] m = new int[n.length-cnt];
            for(int i = 0,k = 0,cnt0 = 0;i < n.length-cnt;i++,k++)
            {
                if(i == cnt_j[cnt0])
                    cnt0++;
                m[i] = n[k + cnt0];
            }
            this.n = m;
        }
    }

    public double sinn(double input) {
        double radians = Math.toRadians(input);
        return Math.sin(radians);
    }

    public double coss(double input) {
        double radians = Math.toRadians(input);
        return Math.cos(radians);
    }

    public double tann(double input) {
        double radians = Math.toRadians(input);
        return Math.tan(radians);
    }

    public double[] paraCal(int[] n1) {//赤纬
        double[] a = new double[n1.length];
        for(int i = 0;i < n1.length;i++)
            a[i] = 23.45*sinn(360.0*(284+n1[i])/365);
        return a;
    }

    public double acoss(double K){
        double M = K;
        if(K < -0.5)
            M = Math.max(K,-1.0);
        if(K >0.5)
            M = Math.min(K,1.0);

        return Math.acos(M);
    }

    public double asinn(double K){
        double M = K;
        if(K < -0.5)
            M = Math.max(K,-1.0);
        if(K >0.5)
            M = Math.min(K,1.0);
        return Math.asin(M);
    }

    public calrb_result CalRb(double lat,double b,double a,double y) {//计算Rb、w_s等
        double aa = sinn(a)*(sinn(lat)*coss(b)-coss(lat)*sinn(b)*coss(y));
        double bb = coss(a)*(coss(lat)*coss(b)+sinn(lat)*sinn(b)*coss(y));
        double c = coss(a)*sinn(b)*sinn(y);
        double D = Math.sqrt(bb*bb+c*c);
        calrb_result output = new calrb_result();
        output.w_s = acoss(-1*tann(lat)*tann(a));
        //output.w_sr = -1*Math.min(output.w_s,Math.abs(-1*acoss(-1*aa/D)+asinn(c/D)));
        output.w_sr = -1*Math.min(output.w_s,-1*acoss(-1*aa/D)+asinn(c/D));
        output.w_ss = Math.min(output.w_s,acoss(-1*aa/D)+asinn(c/D));
        double Rb_numerator = coss(b)*sinn(a)*sinn(lat)*(output.w_ss-output.w_sr)-
                sinn(a)*coss(lat)*sinn(b)*coss(y)*(output.w_ss-output.w_sr)+
                coss(lat)*coss(a)*coss(b)*(Math.sin(output.w_ss)-Math.sin(output.w_sr))+
                coss(a)*coss(y)*sinn(lat)*sinn(b)*(Math.sin(output.w_ss)-Math.sin(output.w_sr))-
                coss(a)*sinn(b)*sinn(y)*(Math.cos(output.w_ss)-Math.cos(output.w_sr));
        double Rb_denominator = coss(lat)*coss(a)*Math.sin(output.w_s)+output.w_s*sinn(lat)*sinn(a);
        output.Rb = Rb_numerator/Rb_denominator/2;
        return output;
    }

    public tilt_result tiltRadiation(double Rb,double lat,double a,double w_s,
                                     double w_sr,double p,double b,double Hb,double Hd,int n) {
        //计算Ht、Uopt
        tilt_result output = new tilt_result();
        double Isc = 1367;
        double H0 = 24/Math.PI*Isc*(1+0.33*coss(360.0*n/365))*(coss(lat)*coss(a)*Math.sin(w_s)+
                w_s*sinn(lat)*sinn(a));
        output.Ht = (Hb*Rb+Hd*(Hb/H0*Rb+0.5*(1+coss(b))*(1-Hb/H0))+p/2*(Hb+Hd)*(1-coss(b)));
        output.Uopt = Math.atan((2*Hb/(Hb+Hd)+2*Hb/H0*(1-Hb/(Hb+Hd)))*(tann(lat)*tann(lat)*Math.tan(w_s)+w_s)/(
                Hb/(Hb+Hd)+Hb/H0*(1-Hb/(Hb+Hd))+1-p)/tann(lat)/(Math.tan(w_s)-w_s))*360/2/Math.PI;
        return output;

    }

    public ArrayList<angle_result> bestAngle(char index,double lat,double[] a,ArrayList<Double> Hb,ArrayList<Double> Hd) {
        ArrayList<calrb_result> angle1 = new ArrayList<calrb_result>();
        ArrayList<tilt_result> angle2 = new ArrayList<tilt_result>();
        ArrayList<calrb_result> angle3 = new ArrayList<calrb_result>();
        ArrayList<tilt_result> angle4 = new ArrayList<tilt_result>();
        double[] output = new double[a.length];
        double[] output2 = new double[a.length];
        double[] Ht_1 = new double[a.length];
        double[] Ht_2 = new double[a.length];
        double[] maxHt = new double[a.length];
        if(index == 'y')
        {
            for(int i = 0;i < a.length;i++)
            {
                angle1.add(CalRb(lat,this.b_0,a[i],0));
                angle2.add(tiltRadiation(angle1.get(i).Rb,lat,a[i],angle1.get(i).w_s,angle1.get(i).w_sr,
                        this.p,this.b_0,Hb.get(i),Hd.get(i),this.n[i]));
                output[i] = angle2.get(i).Uopt;
                output2[i] = angle2.get(i).Ht;
            }

        }
        if(index == 'n')
        {
            for(int i = 0;i < a.length;i++)
            {
                angle1.add(CalRb(lat,this.b_0,a[i],this.y_0));
                angle2.add(tiltRadiation(angle1.get(i).Rb,lat,a[i],angle1.get(i).w_s,angle1.get(i).w_sr,
                        this.p,this.b_0,Hb.get(i),Hd.get(i),this.n[i]));
                Ht_1[i] = angle2.get(i).Ht;
            }
            log.debug("\nHt_1:");
            for(int i = 0;i < Ht_1.length;i++)
                log.debug(Ht_1[i]+" ");
            log.debug("\n");
            for(int i = 0;i < Ht_1.length;i++)
                maxHt[i] = Ht_1[i];
            double[] angle = new double[a.length];
            for(int j = 0;j < a.length;j++)
                angle[j] = 0.0;
            for(int j = 0,k = 0;j < a.length;j++)
                for(double i = -20.0;i < 90.0;i+=0.1,k++)
                {
                    //ArrayList<calrb_result> angle3 = new ArrayList<calrb_result>();
                    //ArrayList<tilt_result> angle4 = new ArrayList<tilt_result>();
                    angle3.add(CalRb(lat,i,a[j],this.y_0));
                    angle4.add(tiltRadiation(angle3.get(k).Rb,lat,a[j],angle3.get(k).w_s,angle3.get(k).w_sr,
                            this.p,i,Hb.get(k%Hb.size()),Hd.get(k%Hb.size()),this.n[j]));
                    Ht_2[j] = angle4.get(k).Ht;
                    if(Ht_2[j] > maxHt[j])
                    {
                        maxHt[j] = Ht_2[j];
                        angle[j] = i;
                    }
                }
            output = angle;
            output2 = Ht_1;
        }

        ArrayList<angle_result> angleResult = new ArrayList<angle_result>();

        for(int i = 0;i < a.length;i++)
        {
            angle_result output_result = new angle_result();
            output_result.Ht = output2[i];
            output_result.Uopt = output[i];
            angleResult.add(output_result);
        }

        return angleResult;
    }

    public int[] sort(double[] Ht_cons) {//排序并返回索引
        double Ht_max = Ht_cons[0];
        int[] index = new int[Ht_cons.length];
        double x;
        int y;
        for (int i = 0;i < Ht_cons.length;i++)
            index[i] = i;
        for(int i = 0;i<Ht_cons.length;i++)
            for(int j = i;j<Ht_cons.length;j++)
                if(Ht_cons[i] > Ht_cons[j])
                {
                    x = Ht_cons[j];
                    Ht_cons[j] = Ht_cons[i];
                    Ht_cons[i] = x;
                    y = index[j];
                    index[j] = index[i];
                    index[i] = y;
                }
        return index;
    }

    public double[] Entropy(double[][] X) {//熵权法
        int n_X = X.length;
        int m_X = X[0].length;
        double sum_row = 0.0;
        double sum_para = 0.0;
        double sum_weight = 0.0;
        double entropy;
        double[] para = new double[n_X];
        double[] weight = new double[m_X];
        double[] weight_norm = new double[m_X];

        for(int col = 0;col < m_X;col++)
        {
            for(int row = 0;row < n_X;row++)
                sum_row += X[row][col];
            for(int row = 0;row < n_X;row++)
            {
                para[row] = X[row][col]/sum_row;
                if(para[row] == 0.0)
                    sum_para += 0;
                else
                    sum_para += para[row]*Math.log(para[row]);
            }
            entropy = -1*sum_para/Math.log(n_X);
            weight[col] = 1-entropy;
            sum_row = 0.0;
            sum_para = 0.0;
        }
        for(int col = 0;col < X[0].length;col++)
            sum_weight += weight[col];
        for(int col = 0;col < X[0].length;col++)
            weight_norm[col] = weight[col]/sum_weight;
        return weight_norm;
    }

    public double[] getD_p(double[][] X,double[] weight) {
        int n_X = X.length;
        int m_X = X[0].length;
        double X_max = X[0][0];
        double[] sum = new double[n_X];
        double[] D_p = new double[n_X];
        for(int row = 0;row < n_X;row++)
            sum[row] = 0.0;
        for(int col = 0;col < m_X;col++)
        {
            for(int row = 0;row < n_X;row++)
                if(X[row][col] > X_max)
                    X_max = X[row][col];
            for(int row = 0;row < n_X;row++)
                sum[row] += (X[row][col]-X_max)*(X[row][col]-X_max)*weight[col];

        }
        for(int row = 0;row < n_X;row++)
            D_p[row] = Math.sqrt(sum[row]);
        return D_p;
    }

    public double[] getD_n(double[][] X,double[] weight) {
        int n_X = X.length;
        int m_X = X[0].length;
        double X_min = X[0][0];
        double[] sum = new double[n_X];
        double[] D_n = new double[n_X];
        for(int row = 0;row < n_X;row++)
            sum[row] = 0.0;
        for(int col = 0;col < m_X;col++)
        {
            for(int row = 0;row < n_X;row++)
                if(X[row][col] < X_min)
                    X_min = X[row][col];
            for(int row = 0;row < n_X;row++)
                sum[row] += (X[row][col]-X_min)*(X[row][col]-X_min)*weight[col];

        }
        for(int row = 0;row < n_X;row++)
            D_n[row] = Math.sqrt(sum[row]);
        return D_n;
    }

    public double[] Topsis(double[][] X) {
        int n_X = X.length;
        int m_X = X[0].length;
        double[] sum_2 = {0.0,0.0};
        double sum_Score = 0.0;
        double[] weight = new double[m_X];
        double[] D_p = new double[n_X];
        double[] D_n = new double[n_X];
        double[] Score = new double[n_X];
        double[] stand_Score = new double[n_X];

        for(int col = 0;col < m_X;col++)
            for(int row = 0;row < n_X;row++)
                sum_2[col] += X[row][col]*X[row][col];
        for(int col = 0;col < m_X;col++)
            for(int row = 0;row < n_X;row++)
                X[row][col] = X[row][col]/Math.sqrt(sum_2[col]);
        weight = Entropy(X);
        D_p = getD_p(X,weight);
        D_n = getD_n(X,weight);
        for(int row = 0;row < n_X;row++)
        {
            Score[row] = D_n[row]/(D_p[row]+D_n[row]);
            sum_Score += Score[row];
        }
        for(int row = 0;row < n_X;row++)
            stand_Score[row] = Score[row]/sum_Score;

        return stand_Score;
    }

    public double Weight(ArrayList<Double> Hb,ArrayList<Double> Hd,int[] index,double[] Uopt) {
        double[][] X = new double[(int) Math.round(index.length/2.0)][2];
        double[][] Y = new double[(int) (index.length-Math.round(index.length/2.0))][2];
        double[] stand_S1 = new double[X.length];
        double[] stand_S2 = new double[Y.length];
        double Uopt_sort1 = 0.0;
        double Uopt_sort2 = 0.0;
        double prop1 = 0.4;
        double prop2 = 1-prop1;

        for(int col = 0;col < 2;col++)
            for(int row = 0;row < Math.round(index.length/2.0);row++)
                if(col == 0)
                    X[row][col] = Hb.get(index[row]);
                else
                    X[row][col] = Hd.get(index[row]);
        for(int col = 0;col < 2;col++)
            for(int row = 0, row_index = (int) Math.round(index.length/2.0); row_index < index.length; row_index++,row++)
                if(col == 0)
                    Y[row][col] = Hb.get(index[row_index]);
                else
                    Y[row][col] = Hd.get(index[row_index]);

        stand_S1 = Topsis(X);
        stand_S2 = Topsis(Y);
        for(int row = 0;row < Math.round(index.length/2.0);row++)
            Uopt_sort1 += Uopt[index[row]]*stand_S1[row];
        for(int row = 0, row_index = (int) Math.round(index.length/2.0); row_index < index.length; row_index++,row++)
            Uopt_sort2 += Uopt[index[row_index]]*stand_S2[row];
        return Uopt_sort1*prop1+Uopt_sort2*prop2;
    }

    //public double[] Uopt1 = new double[n.length];
    public double[] Uopt1;

    public double avrg_bestAngle(char index,double lat,double[] a,ArrayList<Double> Hb,ArrayList<Double> Hd,double y) {
        ArrayList<angle_result> angle_result = new ArrayList<angle_result>();
        double[] Uopt0 = new double[a.length];
        double[] Ht0 = new double[a.length];
        int[] index_Ht = new int[a.length];
        double bestAngle = 0.0;
        double[] HbHd_array = new double[a.length];
        Uopt1 = new double[a.length];

        if(index == 'n')
        {
            this.y_0 = y;
        }
        angle_result = bestAngle(index,lat,a,Hb,Hd);
        for(int i = 0;i < a.length;i++)
        {
            Uopt0[i] = angle_result.get(i).Uopt;
            Ht0[i] = angle_result.get(i).Ht;
            Uopt1[i] = Uopt0[i];
            //System.out.print(Uopt0[i]+" ");
        }
        for(int i = 0;i < a.length;i++)
            HbHd_array[i] = Hb.get(i)+Hd.get(i);
        index_Ht = sort(HbHd_array);
        bestAngle = Weight(Hb,Hd,index_Ht,Uopt0);



        return bestAngle;
    }

    public double[] userHt(char index,double lat,double[] a,ArrayList<Double> Hb,ArrayList<Double> Hd,double b) {
        ArrayList<angle_result> angle_result = new ArrayList<angle_result>();
        double[] Uopt = new double[a.length];
        double[] Ht = new double[a.length];

        this.b_0 = b;

        angle_result = bestAngle(index,lat,a,Hb,Hd);
        for(int i = 0;i < a.length;i++)
        {
            Uopt[i] = angle_result.get(i).Uopt;
            Ht[i] = angle_result.get(i).Ht;
        }

        return Ht;
    }

    public double avrg_userHt(double[] Ht) {
        double sum = 0.0;
        for(int i = 0;i < Ht.length;i++)
            sum += Ht[i];
        return sum/Ht.length;
    }

}

class calrb_result{
    double Rb,w_s,w_sr,w_ss;
}

class tilt_result{
    double Ht,Uopt;
}

class angle_result{
    double Ht,Uopt;
}