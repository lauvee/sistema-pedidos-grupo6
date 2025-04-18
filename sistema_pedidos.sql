CREATE TABLE "USUARIO" (
    "idUser" integer NOT NULL,
    "name" varchar,
	"email" varchar NOT NULL,
	"password" varchar NOT NULL,
    "signUpDate" date NOT NULL,
    "totalSpent" integer NOT NULL,
    "roleFK" integer NOT NULL,
    PRIMARY KEY ("idUser")
);

CREATE TABLE "PRODUCTO" (
    "idProducto" integer NOT NULL,
    "name" varchar NOT NULL,
    "stock" integer NOT NULL,
    "price" integer NOT NULL,
    "description" varchar NOT NULL,
    PRIMARY KEY ("idProducto")
);

CREATE TABLE "PEDIDO" (
    "idPedido" integer NOT NULL,
    "usuarioFK" integer NOT NULL,
    "pedidoFK" integer NOT NULL,
    PRIMARY KEY ("idPedido")
);

CREATE TABLE "ROLES" (
    "idRol" integer NOT NULL,
    "name" varchar(10) NOT NULL CHECK ("name" IN ('ADMIN', 'USER', 'GUEST')),
    "description" varchar NOT NULL,
    PRIMARY KEY ("idRol")
);

CREATE TABLE "PEDIDO_EVENTO" (
    "idEvento" integer NOT NULL,
    "topic" varchar NOT NULL,
    "description" varchar NOT NULL,
    "date" date NOT NULL,
    PRIMARY KEY ("idEvento")
);

ALTER TABLE "USUARIO"
ADD CONSTRAINT "fk_Usuario_roleFK_Roles_idRol"
FOREIGN KEY("roleFK") REFERENCES "ROLES"("idRol")
ON DELETE CASCADE;

ALTER TABLE "PEDIDO"
ADD CONSTRAINT "fk_Pedido_usuarioFK_Usuario_idUser"
FOREIGN KEY("usuarioFK") REFERENCES "USUARIO"("idUser");

ALTER TABLE "PEDIDO"
ADD CONSTRAINT "fk_Pedido_pedidoFK_Producto_idProducto"
FOREIGN KEY("pedidoFK") REFERENCES "PRODUCTO"("idProducto");