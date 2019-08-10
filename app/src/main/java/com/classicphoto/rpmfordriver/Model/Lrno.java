package com.classicphoto.rpmfordriver.Model;

/**
 * Created by shine-2 on 6/9/2017.
 */

public class Lrno {

    public String id;
    public String lr_package;
    public String lr_ConsignorName;
    public String lr_ConsigneeName;
    public String lr_weight;
    public String lr_branchcode;
    public String lr_challanno;
    public String lr_lrdate;
    public String lr_royaltypass;
    public String lr_vehicleno;
    public String lr_truckno;
    public String lr_total;
    public String lr_tobranchcode;
    public String lr_tobranchname;
    public String lr_station;
    public String lr_freight;
    public String lr_companyname;
    public String lr_custfullname;
    public String lr_branchname;
    public String lr_receivedate;
    public String lr_deliverydate;
    public String lr_auditeddate;
    public String lr_status;
    public String lr_adminname;
    public String lr_cntype;
    public String lr_godownname;
    public String lr_podfilename;
    public String lr_usertype;
    public String lr_delpersonname;
    public String lr_mrno;
    public String lr_idclosingentry;
    public String lr_bookingterms;
    public String lr_crossingvehicle;
    public String lr_challanentries;
    public String lr_cbranchcode;
    public String lr_cchallanno;
    public String lr_cbranchname;
    public String lr_cchallandate;
    public String lr_cstations;
    public String lr_cchaltype;
    public String lr_crate;
    public String lr_ctotalweight;
    public String lr_cvehno;
    public String lr_cfreight;
    public String lr_cbokername;
    public String lr_ctotal;
    public String lr_cbalance;
    public String lr_ctransitdate;
    public String lr_ctotalpack;
    public String lr_machineqty;

    public Lrno()
    {}
    public Lrno(String id, String packagename, String consignor, String consignee, String weight, String challanentry, String cchallanno)
    {
        this.id = id;
        this.lr_package=packagename;
        this.lr_ConsignorName=consignor;
        this.lr_ConsigneeName=consignee;
        this.lr_weight=weight;
        this.lr_challanentries = challanentry;
        this.lr_cchallanno = cchallanno;
    }


    public String getid() {
        return id;
    }

    public String getLr_package() {
        return lr_package;
    }

    public String getLr_ConsignorName() {
        return lr_ConsignorName;
    }

    public String getLr_ConsigneeName() {
        return lr_ConsigneeName;
    }

    public String getLr_weight() {
        return lr_weight;
    }

    public String getLr_challanentries() {
        return lr_challanentries;
    }

    public String getLr_cchallanno() {
        return lr_cchallanno;
    }

    public String getmachineqty() {
        return lr_machineqty;
    }



    public void setId(String id) {
        this.id = id;
    }

    public void setLr_package(String lr_package) {
        this.lr_package = lr_package;
    }

    public void setLr_ConsignorName(String lr_consignorName) {
        this.lr_ConsignorName = lr_consignorName;
    }

    public void setLr_ConsigneeName(String lr_consigneeName) {
        this.lr_ConsigneeName = lr_consigneeName;
    }

    public void setLr_weight(String lr_weight) {
        this.lr_weight = lr_weight;
    }

    public void setLr_challanentries(String challanentries) {
        this.lr_challanentries = challanentries;
    }

    public void setLr_cchallanno(String cchallanno) {
        this.lr_cchallanno = cchallanno;
    }

    public void setMachineqty(String machineqty) {
        this.lr_machineqty = machineqty;
    }

}
