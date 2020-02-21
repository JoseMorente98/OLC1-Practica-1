/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josemorente.controlador;

/**
 *
 * @author josem
 */
public class ControladorLexico {
    String lexema = "";
    
    private ControladorLexico() {
    }
    
    public static ControladorLexico getInstance() {
        return ControladorLexicoHolder.INSTANCE;
    }
    
    private static class ControladorLexicoHolder {

        private static final ControladorLexico INSTANCE = new ControladorLexico();
    }
    
    /**
     * OBTENER CONJUNTO
     * @param textoFuente
     */
    public void scanner(String textoFuente){
        int opcion = 0;
        int columna = 0;
        int fila = 1;
        
        for (int i = 0; i < textoFuente.length(); i++){
            char letra = textoFuente.charAt(i);
            columna++;
            
            switch(opcion) {
                case 0:
                    //VALIDACION LETRA
                    if(Character.isLetter(letra))
                    {
                        opcion = 1;
                        lexema += letra;
                    //VALIDACION SALTO DE LINEA
                    } else if (letra == '\n')
                    {
                        opcion = 0;
                        columna = 0;
                        fila++;
                    //VALIDACION DE ESPACIO
                    } else if (Character.isSpaceChar(letra))
                    {
                        opcion = 0;
                    //VALIDACION DE NUMERO
                    } else if (Character.isDigit(letra))
                    {
                        opcion = 2;
                        lexema += letra;
                    }
                    //VALIDACION DE SIMBOLOS
                    else if (Character.isDefined(letra))
                    {
                       switch(letra) {
                            case '>':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Mayor");
                            break;
                            case '~':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Virgulilla");
                            break;
                            case '+':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Suma");
                            break;
                            case '*':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Multiplicacion");
                            break;
                            case '.':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Punto");
                            break;
                            case ',':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Coma");
                            break;
                            case ':':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_DosPuntos");
                            break;
                            case ';':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_PuntoComa");
                            break;
                            case '|':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Pleca");
                            break;
                            case '{':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_LlaveIzquierda");
                            break;
                            case '}':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_LlaveDerecha");
                            break;
                            case '-':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Resta");
                            break;
                            case '%':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Porcentaje");
                            break;
                            case '!':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '#':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '$':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '&':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '(':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case ')':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '=':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '?':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '@':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '[':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case ']':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '^':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '_':
                                ControladorToken.getInstance().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            //INICIO COMENTARIO
                            case '/':
                                opcion = 3;
                                lexema += letra;
                            break;
                            //INICIO COMENTARIO
                            case '<':
                                opcion = 5;
                                lexema += letra;
                            break;
                            //INICIO CADENA
                            case '"':
                                opcion = 8;
                                lexema += letra;
                            break;
                            default:
                                ControladorToken.getInstance().agregarError(fila, columna, String.valueOf(letra), "TK_Desconocido");
                            break;
                        }            
                    }
                break;
                case 1:
                    if (Character.isLetterOrDigit(letra) || letra == '_')
                    {
                        lexema += letra;
                        opcion = 1;
                    } else {
                        if (lexema.equals("CONJ"))
                        {
                            ControladorToken.getInstance().agregarToken(fila, (columna - lexema.length()-1), lexema, "PR_" + lexema);
                        } else {
                            ControladorToken.getInstance().agregarToken(fila, (columna - lexema.length()-1), lexema, "Identificador");
                        }
                        lexema = "";
                        i--;
                        columna--;
                        opcion = 0;
                    }
                break;
                case 2:
                    if (Character.isDigit(letra))
                    {
                        lexema += letra;
                        opcion = 2;
                    }
                    else
                    {
                        ControladorToken.getInstance().agregarToken(fila, columna, lexema, "Digito");
                        lexema = "";
                        i--;
                        columna--;
                        opcion = 0;
                    }
                break;
                case 3:
                    if(letra=='/') {
                        opcion = 4;
                        lexema += letra;
                    }
                break;
                case 4:
                    if (letra != '\n')
                    {
                        lexema += letra;
                        opcion = 4;
                    }
                    else
                    {
                        ControladorToken.getInstance().agregarToken(fila, 0, lexema, "ComentarioLinea");
                        fila++; columna = 0;
                        opcion = 0;
                        lexema = "";
                    }
                break;
                case 5:
                    if(letra=='!') {
                        opcion = 6;
                        lexema += letra;
                    }
                break;
                case 6:
                    if (letra != '!')
                    {
                        if (letra == '\n') { fila++; columna = 0; }
                        lexema += letra;
                        opcion = 6;
                    } else {
                        lexema += letra;
                        opcion = 7;
                    }
                break;
                case 7: 
                    if (letra == '>')
                    {
                        lexema += letra;
                        ControladorToken.getInstance().agregarToken(fila, columna, lexema, "ComentarioMultilinea");
                        //fila++; columna = 0;
                        opcion = 0;
                        lexema = "";
                    }                    
                break;
                case 8:
                    if (letra != '"')
                    {
                        if (letra == '\n') { fila++; columna = 0; }
                        lexema += letra;
                        opcion = 8;
                    }
                    else
                    {
                        opcion = 9;
                        lexema += letra;
                        i--; columna--;
                    }
                break;
                case 9:
                    if (letra == '"')
                    {
                        ControladorToken.getInstance().agregarToken(fila, (columna - lexema.length()), lexema, "Cadena");
                        opcion = 0;
                        lexema = "";
                    }
                break;
                
            }
        }
        
    }
}
