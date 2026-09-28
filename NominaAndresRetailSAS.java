import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

class Constantes {
    static final double SMMLV = 1750905;
    static final double auxTrans = 249095;
    static final double topeAuxTrans = 3501810;
    static final int diaBaseMes = 30;
    static final double aporSalud = 0.04;
    static final double aporPens = 0.04;
}

class Empleado {
    private String cedula;
    private String nombre;
    private String cargo;
    private double salario;
    private int dias;

    Empleado(String cedula, String nombre, String cargo, double salario, int dias) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.cargo = cargo;
        this.salario = salario;
        this.dias = dias;
    }

    String getCedula() {
        return cedula;
    }

    String getNombre() {
        return nombre;
    }

    String getCargo() {
        return cargo;
    }

    double getSalario() {
        return salario;
    }

    int getDias() {
        return dias;
    }

    void setCedula(String cedula) {
        this.cedula = cedula;
    }

    void setNombre(String nombre) {
        this.nombre = nombre;
    }

    void setCargo(String cargo) {
        this.cargo = cargo;
    }

    void setSalario(double salario) {
        this.salario = salario;
    }

    void setDias(int dias) {
        this.dias = dias;
    }
}

class CalcularNomina {

    static void liquidarMesOriginal(ArrayList<Empleado> lista, Locale localeCO) {
        if (lista.isEmpty()) {
            System.out.println("En el momento no hay lista registrado");
            return;
        }

        double totDevengado = 0;
        double totAuxilio = 0;
        double totSalud = 0;
        double totPension = 0;
        double totNeto = 0;

        System.out.println("=== LIQUIDACION DE NOMINA DEL MES ===");
        System.out.printf("%-16s %10s %10s %11s %11s %15s%n", "Empleado", "Devengado", "Aux.Transp", "Salud", "Pension", "Neto a pagar");
        System.out.println("------------------------------------------------------------------------------");

        for (Empleado e : lista) {
            double devengado = Math.round(e.getSalario() / Constantes.diaBaseMes * e.getDias());
            double auxilio = 0;

            if (e.getSalario() <= Constantes.topeAuxTrans) {
                auxilio = Math.round(Constantes.auxTrans / Constantes.diaBaseMes * e.getDias());
            }

            double salud = Math.round(Constantes.aporSalud * devengado);
            double pension = Math.round(Constantes.aporPens * devengado);

            double neto = Math.round(devengado + auxilio - salud - pension);

            totDevengado += devengado;
            totAuxilio += auxilio;
            totSalud += salud;
            totPension += pension;
            totNeto += neto;

            System.out.printf(localeCO, "%-16s %,10.0f %,10.0f %,11.0f %,11.0f %,15.0f%n", e.getNombre(), devengado, auxilio, salud, pension, neto);
        }

        System.out.println("------------------------------------------------------------------------------");
        System.out.printf(localeCO, "%-16s %,10.0f %,10.0f %,11.0f %,11.0f %,15.0f%n", "TOTALES", totDevengado, totAuxilio, totSalud, totPension, totNeto);
        System.out.println("lista liquidados: " + lista.size());

    }
}

public class NominaAndresRetailSAS {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Empleado> empleados = new ArrayList<>();
    static Locale CO = Locale.forLanguageTag("es-CO");

    public static void main(String[] args) {
        empleados.add(new Empleado("1010101010", "Laura Gómez", "Cajera", 1750905., 30));
        empleados.add(new Empleado("1020202020", "Carlos Rincón", "Auxiliar de bodega", 1900000., 28));
        empleados.add(new Empleado("1030303030", "Diana Parra", "Vendedora", 2400000., 30));
        empleados.add(new Empleado("1040404040", "Andrés Molina", "Supervisor de tienda", 3800000., 30));
        empleados.add(new Empleado("1050505050", "Sofía Herrera", "Analista de inventario", 3200000., 25));

        System.out.println("Ingresando al sistema...");

        menu();
        sc.close();
    }

    public static int pos(String cedulaBuscada) { // es un metodo auxiliar
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getCedula().equals(cedulaBuscada)) {
                return i;
            }
        }

        return -1;
    }

    static void menu() {
        int opcion;

        do {
            System.out.println("\n==== NOMINA ANDRES RETAIL S.A.S ====");
            System.out.println("1. Registrar empleado");
            System.out.println("2. consultar todos empleados");
            System.out.println("3. Consultar empleado");
            System.out.println("4. actualizar dato de empleado");
            System.out.println("5. Eliminar empleados");
            System.out.println("6. Liquidar nomina del mes general");
            System.out.println("0. Salir");
            System.out.print("digite la opcion deseada: ");

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());

                switch (opcion) {
                    case 1:
                        registroEmpleado();
                        break;
                    case 2:
                        consultarEmpleados();
                        break;
                    case 3:
                        consultarEmpleado();
                        break;
                    case 4:
                        actualizarDato();
                        break;
                    case 5:
                        eliminarEmpleados();
                        break;
                    case 6:
                        liquidacionMes();
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Por favor ingresa un valor entre (0-6)");
                }
            } catch (NumberFormatException e) {
                System.out.println("No hay respuesta por favor ingrese una opcion");

                opcion = -1;
            }
        } while (opcion != 0);
    }

    static void registroEmpleado() {
        String cedula;
        String respuesta;

        System.out.println("=== REGISTRO DE EMPLEADOS ===");

        do {
            do {
                System.out.print("Cedula: ");
                cedula = sc.nextLine().trim();

                if (cedula.isEmpty()) {
                    System.out.println("No hay respuesta por favor registre la cedula");
                } else if (pos(cedula) != -1) {
                    System.out.println("Ya existe un empleado con esta cedula");
                }
            } while (cedula.isEmpty() || pos(cedula) != -1);

            String nombre;

            do {
                System.out.print("Nombre: ");
                nombre = sc.nextLine().trim();

                if (nombre.isEmpty()) {
                    System.out.println("No hay respuesta por favor registre el nombre");
                }
            } while (nombre.isEmpty());

            String cargo;

            do {
                System.out.print("Cargo: ");
                cargo = sc.nextLine().trim();
                if (cargo.isEmpty()) {
                    System.out.println("No hay respuesta por favor registre el cargo");
                }
            } while (cargo.isEmpty());

            double salario;

            do {
                System.out.print("Salario mensal: ");

                try {
                    salario = Double.parseDouble(sc.nextLine().trim());
                    if (salario < Constantes.SMMLV) {
                        System.out.printf(CO, "El salario no puede ser menor a $%,.0f%n", Constantes.SMMLV);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("No hay respuesta por favor registre el salario");

                    salario = 0;
                }
            } while (salario < Constantes.SMMLV);

            int dia;

            do {
                System.out.print("Dias trabajados: ");

                try {
                    dia = Integer.parseInt(sc.nextLine().trim());
                    if (dia < 0 || dia > 30) {
                        System.out.println("El rango de dias trabajados tiene que ser de (0-30)");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("No hay respuesta por favor registre los dias");

                    dia = -1;
                }
            } while (dia < 0 || dia > 30);

            Empleado registro = new Empleado(cedula, nombre, cargo, salario, dia);
            empleados.add(registro);

            System.out.println("empleado " + nombre + " registrado. Total en nomina: " + empleados.size());

            do {
                System.out.print("Desea ingresar otro empleado? (si/no): ");
                respuesta = sc.nextLine().trim();

                if (respuesta.isEmpty()) {
                    System.out.println("No hay respuesta por favor ingrese (si/no)");
                } else if (!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no")) {
                    System.out.println("Respuesta invalida por favor ingrese (si/no)");
                }

            } while (!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no"));
        } while (respuesta.equalsIgnoreCase("si"));
    }

    static void consultarEmpleados() {
        System.out.println("\n=== LISTADOS DE EMPLEADOS ===");

        if (empleados.isEmpty()) {
            System.out.println("En el momento no hay empleados registrado");
            return;
        }

        System.out.printf("%-4s %-11s %-16s %-24s %15s %5s%n", "POS", "CEDULA", "NOMBRE", "CARGO", "SALARIO", "DIA");
        System.out.println("-----------------------------------------------------------------------------------------");

        for (int i = 0; i < empleados.size(); i++) {
            Empleado e = empleados.get(i);

            System.out.printf(CO, "%-4d %-11s %-16s %-24s %,15.0f %5d%n", i, e.getCedula(), e.getNombre(), e.getCargo(), e.getSalario(), e.getDias());
        }
    }

    static void consultarEmpleado() {
        String respuesta;
        String buscada;
        int pos;

        System.out.println("=== CONSULTAR EMPLEADO ===");

        if (empleados.isEmpty()) {
            System.out.println("En el momento no hay empleados registrado");
            return;
        }

        do {
            do {
                System.out.print("Que cedula desea buscar: ");
                buscada = sc.nextLine().trim();

                pos = pos(buscada);

                if (buscada.isEmpty()) {
                    System.out.println("No hay respuesta por favor registre la cedula");
                } else if (pos == -1) {
                    System.out.println("No se ha encontrado un empleado con la cedula: " + buscada);
                }
            } while (buscada.isEmpty() || pos == -1);

            Empleado e = empleados.get(pos);

            System.out.println("Empleado encontrado con la cedula : " + buscada + " en la posicion " + pos);
            System.out.printf("%-7s: %-16s%n", "Nombre", e.getNombre());
            System.out.printf("%-7s: %-24s%n", "Cargo", e.getCargo());
            System.out.printf(CO, "%-7s: $%-,12.0f%n", "Salario", e.getSalario());
            System.out.printf("%-7s: %-11s%n", "Dias", e.getDias());

            do {
                System.out.print("desea hacer otra consulta? (si/no): ");
                respuesta = sc.nextLine().trim();

                if (respuesta.isEmpty()) {
                    System.out.println("No hay respuesta por favor ingrese (si/no)");
                } else if (!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no")) {
                    System.out.println("Respuesta invalida por favor ingrese (si/no)");
                }
            } while (!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no"));
        } while (respuesta.equalsIgnoreCase("si"));
    }

    static void actualizarDato() {
        String respuesta = "";
        int opcion;
        int pos;
        String tipoDato = "";

        System.out.println("=== ACTUALIZACION DE DATOS ===");

        if (empleados.isEmpty()) {
            System.out.println("En el momento no hay empleados registrado");
            return;
        }

        do {
            do {
                System.out.print("Posicion del empleado que desea modificar los datos del (0-" + (empleados.size() - 1) + "): ");

                try {
                    pos = Integer.parseInt(sc.nextLine().trim());
                    if (pos < 0 || pos > (empleados.size() - 1)) {
                        System.out.println("Posicion invalida digite del (0-" + (empleados.size() - 1) + ")");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("No hay respuesta por favor digite la posicion a modificar");

                    pos = -1;
                }
            } while (pos < 0 || pos > (empleados.size() - 1));

            Empleado e = empleados.get(pos);

            System.out.println("Emplead seleccionado es: " + e.getNombre());

            do {
                System.out.println("que dato desea modificar?");
                System.out.println("1. Cedula");
                System.out.println("2. Nombre");
                System.out.println("3. Cargo");
                System.out.println("4. Salario");
                System.out.println("5. Dia");
                System.out.println("6. Regresar");
                System.out.print("digite la opcion: ");

                try {
                    opcion = Integer.parseInt(sc.nextLine().trim());
                    String nuevoDato = "";

                    if (opcion == 6) {
                        break;
                    }
                    if (opcion >= 1 && opcion <= 5) {
                        do {
                            System.out.print("Digite el nuevo dato: ");
                            nuevoDato = sc.nextLine().trim();
                            if (nuevoDato.isEmpty()) {
                                System.out.println("No hay respuesta por favor digite el dato nuevo deseado:");
                            }
                        } while (nuevoDato.isEmpty());
                    }

                    tipoDato = "";
                    double salarioNuevo;
                    int diaNuevo;

                    switch (opcion) {
                        case 1:
                            if (pos(nuevoDato) != -1 && !e.getCedula().equals(nuevoDato)) {
                                System.out.println("Ya existe un empleado con esta cedula");
                            } else if (e.getCedula().equals(nuevoDato)) {
                                System.out.println("No puedes registrar la misma cedula");
                            } else {
                                e.setCedula(nuevoDato);
                                tipoDato = "cedula a";
                            }
                            break;
                        case 2:
                            e.setNombre(nuevoDato);
                            tipoDato = "nombre a";
                            break;
                        case 3:
                            e.setCargo(nuevoDato);
                            tipoDato = "cargo a";
                            break;
                        case 4:
                            try {
                                salarioNuevo = Double.parseDouble(nuevoDato);

                                if (salarioNuevo < Constantes.SMMLV) {
                                    System.out.printf(CO, "El salario no puede ser menor a $%,.0f%n", Constantes.SMMLV);
                                } else {
                                    e.setSalario(salarioNuevo);
                                    tipoDato = "salario a";
                                }
                            } catch (NumberFormatException ex) {
                                System.out.println("No hay respuesta por favor registre el salario");
                            }
                            break;
                        case 5:
                            try {
                                diaNuevo = Integer.parseInt(nuevoDato);

                                if (diaNuevo < 0 || diaNuevo > 30) {
                                    System.out.println("El rango de dias trabajados tiene que ser de (0-30)");
                                } else {
                                    e.setDias(diaNuevo);
                                    tipoDato = "dia o dias a";
                                }
                            } catch (NumberFormatException ex) {
                                System.out.println("No hay respuesta por favor registre el salario");
                            }
                            break;
                        default:
                            System.out.println("por favor ingrese una opcion del (1-6)");
                    }
                    if (!tipoDato.isEmpty()) {
                        System.out.println("Registro actualizado es " + tipoDato + ": " + nuevoDato + " .Al empleado " + e.getNombre());
                        break;
                    }
                } catch (NumberFormatException em) {
                    System.out.println("No hay respuesta por favor digite la opcion deseada:");
                    opcion = -1;
                }
            } while (opcion != 6);
            if (opcion == 6) {
                respuesta = "no";
            } else {
                do {
                    System.out.print("desea hacer otra actualizacion? (si/no): ");
                    respuesta = sc.nextLine().trim();

                    if (respuesta.isEmpty()) {
                        System.out.println("No hay respuesta por favor ingrese (si/no)");
                    } else if (!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no")) {
                        System.out.println("Respuesta invalida por favor ingrese (si/no)");
                    }
                } while (respuesta.isEmpty() || !respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no"));
            }
        } while (respuesta.equalsIgnoreCase("si"));
    }

    static void eliminarEmpleados() {
        int opcion;
        int pos;

        System.out.println("=== ELIMINAR EMPLEADO ===");
        System.out.println("1. Eliminar una posicion");
        System.out.println("2. Eliminar todos los registros");
        System.out.println("3. Regresar");
        System.out.print("digite la opcion: ");
        opcion = Integer.parseInt(sc.nextLine().trim());

        switch (opcion) {
            case 1:
                String respuesta;
                do {
                    do {
                        System.out.print("Digite la posicion del registro que desea eliminar (0-" + (empleados.size() - 1) + "): ");

                        try {
                            pos = Integer.parseInt(sc.nextLine().trim());
                            if (pos < 0 || pos > (empleados.size() - 1)) {
                                System.out.println("Posicion invalida digite del (0-" + (empleados.size() - 1) + ")");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("No hay respuesta por favor digite la posicion a eliminar");

                            pos = -1;
                        }
                    } while (pos < 0 || pos > (empleados.size() - 1));
                    empleados.remove(pos);

                    System.out.println("Se ha eliminado la posicion " + pos + " con exito");

                    do {
                        System.out.print("desea hacer otra eliminacion? (si/no): ");
                        respuesta = sc.nextLine().trim();
                        if (respuesta.isEmpty()) {
                            System.out.println("No hay respuesta por favor ingrese (si/no)");
                        } else if (!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no")) {
                            System.out.println("Respuesta invalida por favor ingrese (si/no)");
                        }
                    } while (respuesta.isEmpty() || !respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no"));
                } while (respuesta.equalsIgnoreCase("si"));
                break;
            case 2:
                empleados.clear();
                System.out.println("Se ha eliminado todos los registros con exito");
                break;
            case 3:
                break;
        }
    }

    static void liquidacionMes() {
        CalcularNomina.liquidarMesOriginal(empleados, CO);
    }
}