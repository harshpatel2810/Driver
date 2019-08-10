package com.classicphoto.rpmfordriver.Model;

public class Lrno1 {

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
    public String lr_partyinvoice;
    public String lr_stationaddress;
    public String lr_fromstation;


    public Lrno1()
    {}
    public Lrno1(String id, String packagename, String consignor, String consignee, String weight,
                 String challanentry, String cchallanno, String status, String invoice, String date,
                 String pod, String stationaddress, String lr_cchallandate, String lr_cvehno, String lr_cntype) {
        this.id = id;
        this.lr_package=packagename;
        this.lr_ConsignorName=consignor;
        this.lr_ConsigneeName=consignee;
        this.lr_weight=weight;
        this.lr_challanentries = challanentry;
        this.lr_cchallanno = cchallanno;
        this.lr_status = status;
        this.lr_partyinvoice = invoice;
        this.lr_lrdate = date;
        this.lr_podfilename = pod;
        this.lr_stationaddress = stationaddress;
        this.lr_cchallandate = lr_cchallandate;
        this.lr_cvehno = lr_cvehno;
        this.lr_cntype = lr_cntype;
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

    public String getLr_status() {
        return lr_status;
    }

    public String getLr_partyinvoice() {
        return lr_partyinvoice;
    }

    public String getLr_lrdate() {
        return lr_lrdate;
    }

    public String getLr_podfilename() {
        return lr_podfilename;
    }

    public String getLr_stationaddress() {
        return lr_stationaddress;
    }

    public String getLr_cchallandate() {
        return lr_cchallandate;
    }

    public String getLr_cvehno() {
        return lr_cvehno;
    }

    public String getLr_cntype() {
        return lr_cntype;
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

    public void setLr_status(String status) {
        this.lr_status = status;
    }

    public void setLr_partyinvoice(String partyinvoice) {
        this.lr_partyinvoice = partyinvoice;
    }

    public void setLr_lrdate(String lrdate) {
        this.lr_lrdate = lrdate;
    }

    public void setLr_podfilename(String lr_podfilename) {
        this.lr_podfilename = lr_podfilename;
    }

    public void setLr_stationaddress(String lr_stationaddress) {
        this.lr_stationaddress = lr_stationaddress;
    }

    public void setLr_cchallandate(String lr_cchallandate) {
        this.lr_cchallandate = lr_cchallandate;
    }

    public void setLr_cvehno(String lr_cvehno) {
        this.lr_cvehno = lr_cvehno;
    }

    public void setLr_cntype(String lr_cntype) {
        this.lr_cntype = lr_cntype;
    }

    public String getLr_fromstation() {
        return lr_fromstation;
    }

    public void setLr_fromstation(String lr_fromstation) {
        this.lr_fromstation = lr_fromstation;
    }
}
