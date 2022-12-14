package day07;

import java.util.ArrayList;
import java.util.List;

public class Day07Task1 {

    public static void main(String[] args) {
        Directory root = new Directory();
        List<Directory> currentDirectoryTree = new ArrayList<>();
        currentDirectoryTree.add(root);

        for (String line : INPUT.split("\n")) {
            if (line.startsWith("$ ")) {
                line = line.substring(2);
                if (line.equals("ls")) {
                    // ignore
                } else if (line.startsWith("cd ")) {
                    String cdParam = line.substring(3);
                    if (cdParam.equals("/")) {
                        while (currentDirectoryTree.size() > 1) {
                            currentDirectoryTree.remove(currentDirectoryTree.size() - 1);
                        }
                    } else if (cdParam.equals("..")) {
                        currentDirectoryTree.remove(currentDirectoryTree.size() - 1);
                    } else {
                        Directory currentDirectory = currentDirectoryTree.get(currentDirectoryTree.size() - 1);
                        currentDirectoryTree.add(currentDirectory.getAndAddSubdirectory(cdParam));
                    }
                }
            } else {
                // file listing
                Directory currentDirectory = currentDirectoryTree.get(currentDirectoryTree.size() - 1);
                String[] directoryEntry = line.split(" ");
                if (directoryEntry[0].equals("dir")) {
                    currentDirectory.getAndAddSubdirectory(directoryEntry[1]);
                } else {
                    long size = Long.parseLong(directoryEntry[0]);
                    currentDirectory.addFile(directoryEntry[1], size);
                    for (Directory e : currentDirectoryTree) {
                        e.addTotalSize(size);
                    }
                }
            }
        }

        root.print(0);
        System.out.println(Directory.totalSizeUnter100000Count);

        long freeSize = 70000000L - root.getTotalSize();
        long missingSize = 30000000L - freeSize;
        System.out.println("Missing: " + missingSize);
        findSmallestDirectoryWithAtLeast(missingSize, root);
        System.out.println("Missing Min: " + minSizeAtLeastMin);
    }

    private static void findSmallestDirectoryWithAtLeast(long missingSize, Directory current) {
        if (current.getTotalSize() >= missingSize && current.getTotalSize() < minSizeAtLeastMin) {
            minSizeAtLeastMin = current.getTotalSize();
        }
        for (Directory d : current.getSubdirectories()) {
            findSmallestDirectoryWithAtLeast(missingSize, d);
        }
    }

    private static long minSizeAtLeastMin = Long.MAX_VALUE;

    private static final String INPUT = "$ cd /\n"
            + "$ ls\n"
            + "dir ddpgzpc\n"
            + "dir mqjrd\n"
            + "dir mrqjg\n"
            + "dir rglgbsq\n"
            + "298050 tjmjp.cqm\n"
            + "dir wlqhpwqv\n"
            + "$ cd ddpgzpc\n"
            + "$ ls\n"
            + "290515 cvrd.hcf\n"
            + "dir mlm\n"
            + "122034 rrtnthnt.zgs\n"
            + "12680 tvnrl\n"
            + "49534 vljqzqg\n"
            + "dir zffbmlbd\n"
            + "18557 zfhnw.jfd\n"
            + "$ cd mlm\n"
            + "$ ls\n"
            + "102897 zfhnw.zpd\n"
            + "$ cd ..\n"
            + "$ cd zffbmlbd\n"
            + "$ ls\n"
            + "dir bqpwdh\n"
            + "dir gqrlmdhs\n"
            + "315267 mjm.dhc\n"
            + "294364 mrqdw.npr\n"
            + "dir szqz\n"
            + "76621 tvnrl\n"
            + "285948 vpdbrh\n"
            + "155914 vwl.vsq\n"
            + "dir zfhnw\n"
            + "$ cd bqpwdh\n"
            + "$ ls\n"
            + "dir bhmw\n"
            + "27669 dtzw\n"
            + "dir lfhgjw\n"
            + "dir pjqwq\n"
            + "$ cd bhmw\n"
            + "$ ls\n"
            + "190433 zbcbr\n"
            + "$ cd ..\n"
            + "$ cd lfhgjw\n"
            + "$ ls\n"
            + "dir ndrcgmd\n"
            + "$ cd ndrcgmd\n"
            + "$ ls\n"
            + "98160 mjm.dhc\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd pjqwq\n"
            + "$ ls\n"
            + "50937 dtzw\n"
            + "186171 mjm.dhc\n"
            + "305433 mlm\n"
            + "272969 mlm.rhf\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd gqrlmdhs\n"
            + "$ ls\n"
            + "dir blc\n"
            + "331077 dcchtmp\n"
            + "dir mlm\n"
            + "199021 rlzjl\n"
            + "253162 vghhgvjq\n"
            + "dir zfhnw\n"
            + "$ cd blc\n"
            + "$ ls\n"
            + "53872 drjdcqw.szd\n"
            + "115417 ggh.qsl\n"
            + "65105 pjqwq\n"
            + "$ cd ..\n"
            + "$ cd mlm\n"
            + "$ ls\n"
            + "dir bqpwdh\n"
            + "200734 gjhzr.ffz\n"
            + "277561 lwnl.jsl\n"
            + "dir sdjnlsf\n"
            + "dir trqhm\n"
            + "140014 tvnrl\n"
            + "$ cd bqpwdh\n"
            + "$ ls\n"
            + "dir jzfgz\n"
            + "$ cd jzfgz\n"
            + "$ ls\n"
            + "334790 dtzw\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd sdjnlsf\n"
            + "$ ls\n"
            + "326446 mjm.dhc\n"
            + "dir vpdbrh\n"
            + "$ cd vpdbrh\n"
            + "$ ls\n"
            + "20883 bwjjdszc\n"
            + "10518 dtzw\n"
            + "64779 ppmwtlj.btf\n"
            + "320555 rpf.tmw\n"
            + "295126 vwl.vsq\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd trqhm\n"
            + "$ ls\n"
            + "184138 rmnmsl\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd zfhnw\n"
            + "$ ls\n"
            + "dir pjqwq\n"
            + "$ cd pjqwq\n"
            + "$ ls\n"
            + "dir qjzscp\n"
            + "$ cd qjzscp\n"
            + "$ ls\n"
            + "299311 tvnrl\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd szqz\n"
            + "$ ls\n"
            + "dir bqpwdh\n"
            + "107678 jmqq\n"
            + "109752 vtmgq.bcz\n"
            + "301721 zjdlztw\n"
            + "dir zwvzzz\n"
            + "$ cd bqpwdh\n"
            + "$ ls\n"
            + "dir mlm\n"
            + "$ cd mlm\n"
            + "$ ls\n"
            + "178616 mlm.rnn\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd zwvzzz\n"
            + "$ ls\n"
            + "135690 rrbv.ntn\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd zfhnw\n"
            + "$ ls\n"
            + "dir dtgnbb\n"
            + "55267 dtzw\n"
            + "119612 mjm.dhc\n"
            + "$ cd dtgnbb\n"
            + "$ ls\n"
            + "74360 zjq\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd mqjrd\n"
            + "$ ls\n"
            + "dir ccnpn\n"
            + "176761 rmnmsl\n"
            + "$ cd ccnpn\n"
            + "$ ls\n"
            + "116424 pjqwq.ctj\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd mrqjg\n"
            + "$ ls\n"
            + "dir bsphvqnh\n"
            + "266338 lwfdlqzq.wmj\n"
            + "287488 mjm.dhc\n"
            + "211569 mlm.mbn\n"
            + "231144 vpdbrh\n"
            + "260476 vtqjh.wfj\n"
            + "$ cd bsphvqnh\n"
            + "$ ls\n"
            + "101783 pscn.zdh\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd rglgbsq\n"
            + "$ ls\n"
            + "dir bqpwdh\n"
            + "dir fdmhnzw\n"
            + "dir fgz\n"
            + "213313 hbj.lgh\n"
            + "dir lftcr\n"
            + "dir pjqwq\n"
            + "1614 rmnmsl\n"
            + "dir rpz\n"
            + "dir vpcq\n"
            + "dir zfhnw\n"
            + "$ cd bqpwdh\n"
            + "$ ls\n"
            + "35649 mjm.dhc\n"
            + "53750 nqdlf.trh\n"
            + "102195 vpdbrh.lbn\n"
            + "$ cd ..\n"
            + "$ cd fdmhnzw\n"
            + "$ ls\n"
            + "222384 dtzw\n"
            + "$ cd ..\n"
            + "$ cd fgz\n"
            + "$ ls\n"
            + "dir rzrsgst\n"
            + "dir tqdghbj\n"
            + "$ cd rzrsgst\n"
            + "$ ls\n"
            + "120970 dtzw\n"
            + "dir zfhnw\n"
            + "$ cd zfhnw\n"
            + "$ ls\n"
            + "154286 fmbzztww.hvt\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd tqdghbj\n"
            + "$ ls\n"
            + "275314 rmblptm\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd lftcr\n"
            + "$ ls\n"
            + "148378 cwjj.trb\n"
            + "215545 zfhnw.fjl\n"
            + "$ cd ..\n"
            + "$ cd pjqwq\n"
            + "$ ls\n"
            + "dir bppdtc\n"
            + "dir dnlzz\n"
            + "$ cd bppdtc\n"
            + "$ ls\n"
            + "276258 zfhnw.rfn\n"
            + "$ cd ..\n"
            + "$ cd dnlzz\n"
            + "$ ls\n"
            + "286311 cjzm.nhs\n"
            + "239107 ggdr.rgz\n"
            + "dir zfhnw\n"
            + "$ cd zfhnw\n"
            + "$ ls\n"
            + "dir rzht\n"
            + "$ cd rzht\n"
            + "$ ls\n"
            + "100504 thj\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd rpz\n"
            + "$ ls\n"
            + "182300 brsnhb\n"
            + "dir pblmwf\n"
            + "261712 rmnmsl\n"
            + "dir zfhnw\n"
            + "$ cd pblmwf\n"
            + "$ ls\n"
            + "121117 mlm.zdq\n"
            + "$ cd ..\n"
            + "$ cd zfhnw\n"
            + "$ ls\n"
            + "281353 gwbrctf\n"
            + "dir hgpnj\n"
            + "dir lvhwhz\n"
            + "dir mlm\n"
            + "dir pcfljzhm\n"
            + "dir vpdbrh\n"
            + "$ cd hgpnj\n"
            + "$ ls\n"
            + "103619 vwl.vsq\n"
            + "$ cd ..\n"
            + "$ cd lvhwhz\n"
            + "$ ls\n"
            + "236328 bqpwdh.qtn\n"
            + "dir gjwth\n"
            + "118100 jfcmcq\n"
            + "dir lwsdfhg\n"
            + "51327 mjm.dhc\n"
            + "41403 mlm\n"
            + "dir vpdbrh\n"
            + "313830 zmwhlcsw\n"
            + "$ cd gjwth\n"
            + "$ ls\n"
            + "dir bqpwdh\n"
            + "128093 css\n"
            + "290123 pjqwq.djg\n"
            + "89091 whdwbssf.tss\n"
            + "$ cd bqpwdh\n"
            + "$ ls\n"
            + "186274 rmnmsl\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd lwsdfhg\n"
            + "$ ls\n"
            + "218938 mjm.dhc\n"
            + "$ cd ..\n"
            + "$ cd vpdbrh\n"
            + "$ ls\n"
            + "139398 lrrjnvr\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd mlm\n"
            + "$ ls\n"
            + "278462 fdlb.jsr\n"
            + "176936 tvnrl\n"
            + "29208 vwl.vsq\n"
            + "$ cd ..\n"
            + "$ cd pcfljzhm\n"
            + "$ ls\n"
            + "295983 nnvq.lcg\n"
            + "$ cd ..\n"
            + "$ cd vpdbrh\n"
            + "$ ls\n"
            + "16998 lswwmjc.vmv\n"
            + "52872 pmbzp.mmg\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd vpcq\n"
            + "$ ls\n"
            + "dir tnrpllzj\n"
            + "$ cd tnrpllzj\n"
            + "$ ls\n"
            + "226232 nqrjs.qds\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd zfhnw\n"
            + "$ ls\n"
            + "188324 dtzw\n"
            + "263511 lnwwh\n"
            + "217287 lst.wvw\n"
            + "178366 vzctflm\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd wlqhpwqv\n"
            + "$ ls\n"
            + "dir bqpwdh\n"
            + "dir ffw\n"
            + "dir lpzgcrd\n"
            + "dir lszdbd\n"
            + "51178 mlm\n"
            + "dir ntcpvg\n"
            + "dir pjqwq\n"
            + "dir pmpftw\n"
            + "dir ppf\n"
            + "dir vpdbrh\n"
            + "dir zfhnw\n"
            + "$ cd bqpwdh\n"
            + "$ ls\n"
            + "194389 dnqngfzh\n"
            + "$ cd ..\n"
            + "$ cd ffw\n"
            + "$ ls\n"
            + "dir mfqd\n"
            + "dir npgnwwf\n"
            + "dir tcvt\n"
            + "$ cd mfqd\n"
            + "$ ls\n"
            + "214846 vwl.vsq\n"
            + "$ cd ..\n"
            + "$ cd npgnwwf\n"
            + "$ ls\n"
            + "dir ddqsmtsj\n"
            + "dir gcq\n"
            + "dir ldtpnr\n"
            + "1802 vwl.vsq\n"
            + "$ cd ddqsmtsj\n"
            + "$ ls\n"
            + "309790 rmnmsl\n"
            + "$ cd ..\n"
            + "$ cd gcq\n"
            + "$ ls\n"
            + "80203 lvqhwzfn\n"
            + "$ cd ..\n"
            + "$ cd ldtpnr\n"
            + "$ ls\n"
            + "dir spzj\n"
            + "123522 tvnrl\n"
            + "$ cd spzj\n"
            + "$ ls\n"
            + "139171 bpgpdzt.zzp\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd tcvt\n"
            + "$ ls\n"
            + "dir jcvcjz\n"
            + "dir qmtcr\n"
            + "dir vpdbrh\n"
            + "$ cd jcvcjz\n"
            + "$ ls\n"
            + "274564 hsv.wsr\n"
            + "309010 vpdbrh\n"
            + "$ cd ..\n"
            + "$ cd qmtcr\n"
            + "$ ls\n"
            + "dir mfjd\n"
            + "dir pmbdsb\n"
            + "$ cd mfjd\n"
            + "$ ls\n"
            + "202111 vpdbrh\n"
            + "$ cd ..\n"
            + "$ cd pmbdsb\n"
            + "$ ls\n"
            + "dir brghd\n"
            + "313440 chwzrz.bmf\n"
            + "$ cd brghd\n"
            + "$ ls\n"
            + "216516 dtzw\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd vpdbrh\n"
            + "$ ls\n"
            + "134552 sbs.bsn\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd lpzgcrd\n"
            + "$ ls\n"
            + "244257 bqpwdh.hsz\n"
            + "118275 flgfbstp.flp\n"
            + "dir gcwg\n"
            + "dir mlm\n"
            + "dir nfj\n"
            + "189443 rtwwbgfs.nvl\n"
            + "dir trbwtdb\n"
            + "dir vpdbrh\n"
            + "dir ztwbpvbq\n"
            + "$ cd gcwg\n"
            + "$ ls\n"
            + "dir bqpwdh\n"
            + "304960 dtzw\n"
            + "9496 pfpwtsp\n"
            + "dir pjqwq\n"
            + "dir vpdbrh\n"
            + "dir vqp\n"
            + "186883 vwl.vsq\n"
            + "$ cd bqpwdh\n"
            + "$ ls\n"
            + "79064 fbjdqsn.cgp\n"
            + "$ cd ..\n"
            + "$ cd pjqwq\n"
            + "$ ls\n"
            + "106371 cplcj\n"
            + "204740 mhdq.lhc\n"
            + "313462 pjqwq.lsn\n"
            + "249876 rmnmsl\n"
            + "209574 vwl.vsq\n"
            + "$ cd ..\n"
            + "$ cd vpdbrh\n"
            + "$ ls\n"
            + "166549 mjm.dhc\n"
            + "270734 rmnmsl\n"
            + "$ cd ..\n"
            + "$ cd vqp\n"
            + "$ ls\n"
            + "dir nbq\n"
            + "dir nts\n"
            + "dir rlbhdgm\n"
            + "dir srvqpq\n"
            + "dir zfhnw\n"
            + "$ cd nbq\n"
            + "$ ls\n"
            + "63369 mjm.dhc\n"
            + "314393 smd\n"
            + "70181 tbwpwtt.ccj\n"
            + "97954 vpdbrh.fmw\n"
            + "$ cd ..\n"
            + "$ cd nts\n"
            + "$ ls\n"
            + "11300 zfhnw.pnj\n"
            + "$ cd ..\n"
            + "$ cd rlbhdgm\n"
            + "$ ls\n"
            + "dir bzd\n"
            + "dir hfhzj\n"
            + "65400 mbrqjnp.wqz\n"
            + "dir pztwz\n"
            + "$ cd bzd\n"
            + "$ ls\n"
            + "dir bqpwdh\n"
            + "168832 cdlg.zhp\n"
            + "dir dtb\n"
            + "22418 fttt.twt\n"
            + "dir gmlgvnq\n"
            + "101839 hnpjbjsc.whd\n"
            + "dir pdmqn\n"
            + "122491 smvjvw\n"
            + "dir wmtdbrqm\n"
            + "52142 zfhnw.gmt\n"
            + "$ cd bqpwdh\n"
            + "$ ls\n"
            + "dir btb\n"
            + "37220 gzj.mhf\n"
            + "dir lwl\n"
            + "112215 qcfqd.fwz\n"
            + "210303 qlwgqnsp\n"
            + "dir trpm\n"
            + "$ cd btb\n"
            + "$ ls\n"
            + "dir rqftrtb\n"
            + "dir vsb\n"
            + "$ cd rqftrtb\n"
            + "$ ls\n"
            + "dir ndwphjw\n"
            + "dir pjqwq\n"
            + "dir zfhnw\n"
            + "$ cd ndwphjw\n"
            + "$ ls\n"
            + "256159 lpprpwjq.srz\n"
            + "$ cd ..\n"
            + "$ cd pjqwq\n"
            + "$ ls\n"
            + "dir fpb\n"
            + "$ cd fpb\n"
            + "$ ls\n"
            + "42692 pjqwq\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd zfhnw\n"
            + "$ ls\n"
            + "dir bqpwdh\n"
            + "$ cd bqpwdh\n"
            + "$ ls\n"
            + "17467 mshfwzv.ppr\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd vsb\n"
            + "$ ls\n"
            + "278554 rmnmsl\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd lwl\n"
            + "$ ls\n"
            + "28409 mjm.dhc\n"
            + "$ cd ..\n"
            + "$ cd trpm\n"
            + "$ ls\n"
            + "dir mlm\n"
            + "$ cd mlm\n"
            + "$ ls\n"
            + "304742 dtzw\n"
            + "108223 mjm.dhc\n"
            + "dir mvh\n"
            + "52532 nzc.vhj\n"
            + "dir tdhrrhm\n"
            + "$ cd mvh\n"
            + "$ ls\n"
            + "99770 cgfw.pgm\n"
            + "$ cd ..\n"
            + "$ cd tdhrrhm\n"
            + "$ ls\n"
            + "326653 lrmsnt.fdh\n"
            + "157903 mlm\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd dtb\n"
            + "$ ls\n"
            + "179072 vpdbrh\n"
            + "3435 vpdbrh.hpv\n"
            + "$ cd ..\n"
            + "$ cd gmlgvnq\n"
            + "$ ls\n"
            + "dir rrjgswsd\n"
            + "$ cd rrjgswsd\n"
            + "$ ls\n"
            + "dir zfhnw\n"
            + "$ cd zfhnw\n"
            + "$ ls\n"
            + "278562 mvqbv\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd pdmqn\n"
            + "$ ls\n"
            + "233744 pjqwq\n"
            + "$ cd ..\n"
            + "$ cd wmtdbrqm\n"
            + "$ ls\n"
            + "dir lngc\n"
            + "dir wgpwcj\n"
            + "225374 wphwht.nvn\n"
            + "$ cd lngc\n"
            + "$ ls\n"
            + "4415 zfhnw\n"
            + "$ cd ..\n"
            + "$ cd wgpwcj\n"
            + "$ ls\n"
            + "165889 bqpwdh.ngg\n"
            + "331254 dlpr\n"
            + "97910 mzjlswr.spn\n"
            + "dir rqhshd\n"
            + "49222 vwl.vsq\n"
            + "$ cd rqhshd\n"
            + "$ ls\n"
            + "145902 qwhr\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd hfhzj\n"
            + "$ ls\n"
            + "92623 ldlpnvw\n"
            + "146918 mjm.dhc\n"
            + "$ cd ..\n"
            + "$ cd pztwz\n"
            + "$ ls\n"
            + "dir jllmcfjf\n"
            + "$ cd jllmcfjf\n"
            + "$ ls\n"
            + "245363 dtzw\n"
            + "81345 mbh.vdq\n"
            + "164199 ntwzgfr\n"
            + "14466 rmnmsl\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd srvqpq\n"
            + "$ ls\n"
            + "271019 zfhnw.rlc\n"
            + "$ cd ..\n"
            + "$ cd zfhnw\n"
            + "$ ls\n"
            + "104520 bqpwdh.qqv\n"
            + "12312 lspg\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd mlm\n"
            + "$ ls\n"
            + "259906 cbgmp\n"
            + "dir rjshqvb\n"
            + "$ cd rjshqvb\n"
            + "$ ls\n"
            + "309983 mlm.qmm\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd nfj\n"
            + "$ ls\n"
            + "44759 mlm\n"
            + "228634 njrrs.sjj\n"
            + "dir rfmw\n"
            + "$ cd rfmw\n"
            + "$ ls\n"
            + "273185 bcbjq.vlw\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd trbwtdb\n"
            + "$ ls\n"
            + "307053 mjm.dhc\n"
            + "301028 zzg\n"
            + "$ cd ..\n"
            + "$ cd vpdbrh\n"
            + "$ ls\n"
            + "dir bzdp\n"
            + "169466 grnvt.mst\n"
            + "dir pjqwq\n"
            + "123590 vwl.vsq\n"
            + "$ cd bzdp\n"
            + "$ ls\n"
            + "225941 trrzqz\n"
            + "241249 vpdbrh.lsj\n"
            + "$ cd ..\n"
            + "$ cd pjqwq\n"
            + "$ ls\n"
            + "dir ddfpql\n"
            + "dir fgbqzm\n"
            + "329174 mjm.dhc\n"
            + "6701 mlm.ffp\n"
            + "dir phf\n"
            + "$ cd ddfpql\n"
            + "$ ls\n"
            + "103799 lpbp.bpt\n"
            + "$ cd ..\n"
            + "$ cd fgbqzm\n"
            + "$ ls\n"
            + "dir spsz\n"
            + "$ cd spsz\n"
            + "$ ls\n"
            + "34049 mfgph\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd phf\n"
            + "$ ls\n"
            + "84883 qdj.hbt\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ztwbpvbq\n"
            + "$ ls\n"
            + "138429 bqpwdh.mlr\n"
            + "151403 cqmbgfdh.gvh\n"
            + "9087 ngm\n"
            + "335933 sswtt\n"
            + "318963 tvnrl\n"
            + "dir wdhjpzp\n"
            + "$ cd wdhjpzp\n"
            + "$ ls\n"
            + "119932 pjqwq\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd lszdbd\n"
            + "$ ls\n"
            + "dir cpqpvbz\n"
            + "dir hnl\n"
            + "dir llprt\n"
            + "$ cd cpqpvbz\n"
            + "$ ls\n"
            + "dir ltlcz\n"
            + "dir wmpsvm\n"
            + "$ cd ltlcz\n"
            + "$ ls\n"
            + "262150 zfhnw.zsg\n"
            + "$ cd ..\n"
            + "$ cd wmpsvm\n"
            + "$ ls\n"
            + "dir bqpwdh\n"
            + "$ cd bqpwdh\n"
            + "$ ls\n"
            + "51488 pvhcb.qmw\n"
            + "44038 zfhnw\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd hnl\n"
            + "$ ls\n"
            + "dir pjqwq\n"
            + "$ cd pjqwq\n"
            + "$ ls\n"
            + "170454 mhg.ddj\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd llprt\n"
            + "$ ls\n"
            + "268114 bmvwwbdt.cqm\n"
            + "207425 dtzw\n"
            + "180660 mgqz\n"
            + "297846 qbpcd\n"
            + "112867 zdb\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ntcpvg\n"
            + "$ ls\n"
            + "74161 bqpwdh.gbr\n"
            + "257792 vwl.vsq\n"
            + "$ cd ..\n"
            + "$ cd pjqwq\n"
            + "$ ls\n"
            + "279738 hwdgzvj\n"
            + "dir jsdbnwrc\n"
            + "dir pcjfjsgs\n"
            + "11113 rqrtcq\n"
            + "208212 tvnrl\n"
            + "dir vllzsh\n"
            + "$ cd jsdbnwrc\n"
            + "$ ls\n"
            + "11720 fvj\n"
            + "$ cd ..\n"
            + "$ cd pcjfjsgs\n"
            + "$ ls\n"
            + "dir bqpwdh\n"
            + "195046 mjm.dhc\n"
            + "dir ssq\n"
            + "dir vpdbrh\n"
            + "$ cd bqpwdh\n"
            + "$ ls\n"
            + "42769 dlrvsj\n"
            + "159280 zfhnw\n"
            + "239759 zqqcb\n"
            + "$ cd ..\n"
            + "$ cd ssq\n"
            + "$ ls\n"
            + "67639 bqpwdh.csb\n"
            + "$ cd ..\n"
            + "$ cd vpdbrh\n"
            + "$ ls\n"
            + "dir bqdpwrst\n"
            + "dir qtj\n"
            + "$ cd bqdpwrst\n"
            + "$ ls\n"
            + "57800 fndpnj.fgt\n"
            + "132712 vpdbrh\n"
            + "$ cd ..\n"
            + "$ cd qtj\n"
            + "$ ls\n"
            + "dir szjtvcb\n"
            + "$ cd szjtvcb\n"
            + "$ ls\n"
            + "93993 mgmqtdb.fzd\n"
            + "dir stbczmlq\n"
            + "$ cd stbczmlq\n"
            + "$ ls\n"
            + "dir nhq\n"
            + "$ cd nhq\n"
            + "$ ls\n"
            + "27749 hqgngdt.tmq\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd vllzsh\n"
            + "$ ls\n"
            + "dir nlwwrz\n"
            + "237293 wlgbt\n"
            + "dir zhmwl\n"
            + "$ cd nlwwrz\n"
            + "$ ls\n"
            + "99990 bjv.szl\n"
            + "$ cd ..\n"
            + "$ cd zhmwl\n"
            + "$ ls\n"
            + "dir hbpps\n"
            + "dir hfv\n"
            + "$ cd hbpps\n"
            + "$ ls\n"
            + "7520 mlm.ltq\n"
            + "$ cd ..\n"
            + "$ cd hfv\n"
            + "$ ls\n"
            + "dir qpfrd\n"
            + "$ cd qpfrd\n"
            + "$ ls\n"
            + "dir mlm\n"
            + "$ cd mlm\n"
            + "$ ls\n"
            + "288919 qmtpwqn\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd pmpftw\n"
            + "$ ls\n"
            + "118859 mlm\n"
            + "103896 pjqwq\n"
            + "128800 tvnrl\n"
            + "$ cd ..\n"
            + "$ cd ppf\n"
            + "$ ls\n"
            + "dir drszpqf\n"
            + "dir fbs\n"
            + "202594 gdpw.bds\n"
            + "dir ldnrpg\n"
            + "176398 mbbmmf.plr\n"
            + "dir tfjnj\n"
            + "$ cd drszpqf\n"
            + "$ ls\n"
            + "dir pjqwq\n"
            + "dir qtblb\n"
            + "191392 tvnrl\n"
            + "$ cd pjqwq\n"
            + "$ ls\n"
            + "dir lrrlbgwh\n"
            + "dir nfcc\n"
            + "dir pqm\n"
            + "$ cd lrrlbgwh\n"
            + "$ ls\n"
            + "182434 mjm.dhc\n"
            + "238706 vpdbrh.lgz\n"
            + "$ cd ..\n"
            + "$ cd nfcc\n"
            + "$ ls\n"
            + "253846 vpdbrh\n"
            + "268229 vwl.vsq\n"
            + "$ cd ..\n"
            + "$ cd pqm\n"
            + "$ ls\n"
            + "56573 vwl.vsq\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd qtblb\n"
            + "$ ls\n"
            + "28941 zcm.dtw\n"
            + "52282 zmhw.lhm\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd fbs\n"
            + "$ ls\n"
            + "dir gpttw\n"
            + "$ cd gpttw\n"
            + "$ ls\n"
            + "dir bqpwdh\n"
            + "$ cd bqpwdh\n"
            + "$ ls\n"
            + "98780 wvzhlfht.rdd\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ldnrpg\n"
            + "$ ls\n"
            + "205523 bqpwdh.qlb\n"
            + "54924 pcq.clf\n"
            + "$ cd ..\n"
            + "$ cd tfjnj\n"
            + "$ ls\n"
            + "237752 bqpwdh.bvf\n"
            + "dir lwl\n"
            + "295520 mjm.dhc\n"
            + "dir qsgpsmzw\n"
            + "278576 rmnmsl\n"
            + "dir vljqlw\n"
            + "225025 vwl.vsq\n"
            + "100780 zgjhtrv\n"
            + "$ cd lwl\n"
            + "$ ls\n"
            + "150713 dhrl\n"
            + "$ cd ..\n"
            + "$ cd qsgpsmzw\n"
            + "$ ls\n"
            + "265288 bqpwdh\n"
            + "92636 ntgrlr\n"
            + "182224 wdb\n"
            + "$ cd ..\n"
            + "$ cd vljqlw\n"
            + "$ ls\n"
            + "dir pcnd\n"
            + "dir pjqwq\n"
            + "317809 tvnrl\n"
            + "$ cd pcnd\n"
            + "$ ls\n"
            + "8283 gmq\n"
            + "195909 rmnmsl\n"
            + "183891 tvnrl\n"
            + "182837 vwl.vsq\n"
            + "$ cd ..\n"
            + "$ cd pjqwq\n"
            + "$ ls\n"
            + "dir vwp\n"
            + "$ cd vwp\n"
            + "$ ls\n"
            + "dir crpztfmf\n"
            + "dir fhrfrbqg\n"
            + "$ cd crpztfmf\n"
            + "$ ls\n"
            + "257441 dpztgnd\n"
            + "$ cd ..\n"
            + "$ cd fhrfrbqg\n"
            + "$ ls\n"
            + "64573 mjm.dhc\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd vpdbrh\n"
            + "$ ls\n"
            + "80449 mjm.dhc\n"
            + "266777 qfjwb\n"
            + "dir qzmz\n"
            + "100029 tvnrl\n"
            + "28910 zqnp\n"
            + "$ cd qzmz\n"
            + "$ ls\n"
            + "9583 wsfwpznj\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd zfhnw\n"
            + "$ ls\n"
            + "dir pmdsb\n"
            + "106595 vwl.vsq\n"
            + "dir zdv\n"
            + "$ cd pmdsb\n"
            + "$ ls\n"
            + "dir bqpwdh\n"
            + "dir pjqwq\n"
            + "$ cd bqpwdh\n"
            + "$ ls\n"
            + "dir tstqlh\n"
            + "143862 vpdbrh.thr\n"
            + "$ cd tstqlh\n"
            + "$ ls\n"
            + "119310 tcmglrz.hzp\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd pjqwq\n"
            + "$ ls\n"
            + "56885 rmnmsl\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd zdv\n"
            + "$ ls\n"
            + "209148 nhcdqmd.hgh\n"
            + "dir pjdhn\n"
            + "119411 pjqwq.vrq\n"
            + "154423 rmnmsl\n"
            + "178813 vhbjqhhq.tbf\n"
            + "$ cd pjdhn\n"
            + "$ ls\n"
            + "dir gnthzp\n"
            + "116732 qhrz.ssb\n"
            + "dir rvbw\n"
            + "117225 svmpwv\n"
            + "$ cd gnthzp\n"
            + "$ ls\n"
            + "dir bqpwdh\n"
            + "$ cd bqpwdh\n"
            + "$ ls\n"
            + "312253 rmnmsl\n"
            + "$ cd ..\n"
            + "$ cd ..\n"
            + "$ cd rvbw\n"
            + "$ ls\n"
            + "dir cjdhwbv\n"
            + "268173 lsmmthf\n"
            + "99445 vwl.vsq\n"
            + "$ cd cjdhwbv\n"
            + "$ ls\n"
            + "302711 tbhb\n"
            + "173182 tmj.frb";
}
