import { Dialog, DialogContent, DialogOverlay, DialogPortal, DialogTitle } from '@radix-ui/react-dialog';
import { X } from 'lucide-react';
import { ReactNode } from 'react';

interface ModalProps {
  open: boolean;
  onOpenChange: (open: boolean) => void;
  title: string;
  children: ReactNode;
}

export function Modal({ open, onOpenChange, title, children }: ModalProps) {
  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogPortal>
        <DialogOverlay className="fixed inset-0 bg-black/70 backdrop-blur-sm z-50" />
        <DialogContent className="fixed top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 rounded-2xl p-6 w-[90vw] max-w-sm z-50 shadow-2xl" style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.08)' }}>
          <div className="flex items-center justify-between mb-4">
            <DialogTitle style={{ color: '#f5f5f5', fontSize: '18px', fontWeight: 700 }}>
              {title}
            </DialogTitle>
            <button
              onClick={() => onOpenChange(false)}
              style={{ color: '#888888' }}
            >
              <X size={20} />
            </button>
          </div>
          {children}
        </DialogContent>
      </DialogPortal>
    </Dialog>
  );
}
