import { AlertDialog, AlertDialogContent, AlertDialogOverlay, AlertDialogPortal, AlertDialogTitle, AlertDialogDescription } from '@radix-ui/react-alert-dialog';
import { Button } from './button';

interface AlertModalProps {
  open: boolean;
  onOpenChange: (open: boolean) => void;
  title: string;
  description: string;
  onConfirm: () => void;
  confirmText?: string;
  cancelText?: string;
}

export function AlertModal({
  open,
  onOpenChange,
  title,
  description,
  onConfirm,
  confirmText = 'Eliminar',
  cancelText = 'Cancelar'
}: AlertModalProps) {
  return (
    <AlertDialog open={open} onOpenChange={onOpenChange}>
      <AlertDialogPortal>
        <AlertDialogOverlay className="fixed inset-0 bg-black/70 backdrop-blur-sm z-50" />
        <AlertDialogContent className="fixed top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 rounded-2xl p-6 w-[90vw] max-w-sm z-50 shadow-2xl" style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.08)' }}>
          <AlertDialogTitle style={{ color: '#f5f5f5', fontSize: '18px', fontWeight: 700, marginBottom: '8px' }}>
            {title}
          </AlertDialogTitle>
          <AlertDialogDescription style={{ color: '#888888', marginBottom: '24px' }}>
            {description}
          </AlertDialogDescription>
          <div className="flex gap-3 justify-end">
            <Button variant="ghost" onClick={() => onOpenChange(false)}>
              {cancelText}
            </Button>
            <Button 
              variant="danger" 
              onClick={() => {
                onConfirm();
                onOpenChange(false);
              }}
            >
              {confirmText}
            </Button>
          </div>
        </AlertDialogContent>
      </AlertDialogPortal>
    </AlertDialog>
  );
}
