import java.util.List;

public class Main {

    static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("   IAM VISIBILITY SIMULATOR       ");
        System.out.println("==========================================\n");
        IAMService iamService = new IAMService();
        System.out.println("--- CHECKING ACCESS RIGHTS ---");
        boolean annaCanDelete = iamService.checkAccess("Anna_Admin", "DELETE_USER");
        System.out.println("Darf 'Anna_Admin' User löschen? -> " + annaCanDelete);
        boolean benCanDelete = iamService.checkAccess("Ben_Buchhalter", "DELETE_USER");
        System.out.println("Darf 'Ben_Buchhalter' User löschen? -> " + benCanDelete);
        boolean benCanReadFinance = iamService.checkAccess("Ben_Buchhalter", "READ_FINANCE_DATA");
        System.out.println("Darf 'Ben_Buchhalter' Finanzen lesen? -> " + benCanReadFinance);
        System.out.println("\n--- RUNNING SECURITY AUDIT ---");
        SecurityAuditor auditor = new SecurityAuditor();
        List<String> orphans = auditor.findOrphanedAccounts();
        System.out.println("ALARM: Orphaned Accounts gefunden: " + orphans);
        List<String> sodConflicts = auditor.checkSoDConflict(1, 2);
        if (sodConflicts.isEmpty()) {
            System.out.println("OK: Keine SoD-Konflikte zwischen Admin und Finance gefunden.");
        } else {
            System.out.println("KRITISCH: SoD Konflikt! Folgende User haben unzulässige Rollenkombinationen: " + sodConflicts);
        }
        System.out.println("\n==========================================");
        System.out.println("   SYSTEM CHECK BEENDET                   ");
        System.out.println("==========================================");
    }
}